package jpabook.jpashop.service;

import jpabook.jpashop.constant.ExceptionType;
import static jpabook.jpashop.constant.NumberName.*;

import jpabook.jpashop.domain.follow.Follow;
import jpabook.jpashop.domain.follow.FollowRepository;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.MemberRepository;
import jpabook.jpashop.domain.todo.Todo;
import jpabook.jpashop.domain.todo.TodoRepository;
import jpabook.jpashop.dtos.ElseDto;
import jpabook.jpashop.dtos.FormData;
import jpabook.jpashop.dtos.ResponseDto;
import jpabook.jpashop.dtos.ServiceDto;
import jpabook.jpashop.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final FollowRepository followRepository;
    private final TodoRepository todoRepository;

    /* 프로필 이미지 업로드 */
    @Transactional
    public void uploadProfile(Long loginMemberId, MultipartFile file){
        UUID uuid =UUID.randomUUID();
        String uploadFileName = uuid + "_" + file.getOriginalFilename();

        Path imageFilePath = Paths.get(FILE_UPLOAD_FOLDER+ uploadFileName);


        Member loginMember = memberRepository.findById(loginMemberId)
                .orElseThrow(() -> new CustomException(ExceptionType.NOT_EXIST_MEMBER));

        Path uploadPath = Paths.get(FILE_UPLOAD_FOLDER);
        /* 기존 업로드 파일 존재한다면 삭제 */
        if (loginMember.getFileLocation()!=null) {
            Path oldImageFilePath = Paths.get(FILE_UPLOAD_FOLDER + loginMember.getFileLocation());


            if (Files.exists(oldImageFilePath)) {
                try {
                    Files.delete(oldImageFilePath);
                }catch (IOException e ){
                    throw new CustomException(ExceptionType.NOT_DELETE_OLD_FILE);
                }
            }
        }
        /* 새로운 업로드 파일 저장 */
        try {
            if (! Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.write(imageFilePath, file.getBytes());

        }catch(IOException e){
            throw new CustomException(ExceptionType.FILE_UPLOAD_FAILED);
        }

        loginMember.uploadProfileImageLocation(uploadFileName);
    }
    
    /* 회원 피드 */
    @Transactional
    public ResponseDto.MemberFeed find(Long id,Long loginMemberId){

        ResponseDto.MemberFeed settingDto = new ResponseDto.MemberFeed();
        Optional<Follow> checkFollow = followRepository.findByFollowingIdAndFollowerId(id,loginMemberId);
        int totalFollowerCount = followRepository.countByFollowerId(id);
        int totalFollowingCount = followRepository.countByFollowingId(id);

        checkFollow.ifPresent( isFollowed -> { settingDto.changFollowState() ;});

        /* Member Data Setting */
        Member member = memberRepository.findById(id)
                .orElseThrow( () -> new CustomException(ExceptionType.NOT_EXIST_MEMBER));

        if(id!=loginMemberId){
            member.upViewCount();
        }

        settingDto.settingMemberData(member,totalFollowingCount,totalFollowerCount);

        /* Top Member */
        List<Member> getTopMembers = memberRepository.findTop6ByOrderByTotalViewCountDesc();
        List<ServiceDto.TopMember> topMembers =
                getTopMembers.stream().map(topMember ->
                        new ServiceDto.TopMember(topMember)).collect(Collectors.toList());

        /* Todo Data Setting */
        List<Todo> byMemberId = todoRepository.findByMemberIdOrderByCreateDateDesc(id);
        List<ResponseDto.FindTodo> todos =
                byMemberId.stream().map(todo ->
                        new ResponseDto.FindTodo(todo)).collect(Collectors.toList());

        /* Else Data Setting */
        List<Member> byDeveloperPosition = memberRepository.findTop5ByDeveloperPosition(member.getDeveloperPosition());

        List<ElseDto.SameDevPosition> sameDevMembers =
                byDeveloperPosition.stream().map(elseMember ->
                        new ElseDto.SameDevPosition(elseMember)).collect(Collectors.toList());

        settingDto.settingElseData(sameDevMembers,todos,topMembers);

        return settingDto;
    }

    /* 회원 가입 */
    public void join(FormData.Join joinData){
        validateDuplicate(joinData.getUsername());

        Member joinMember = new Member(joinData,bCryptPasswordEncoder);

        memberRepository.save(joinMember);

    }

    public long[] count(){
        long[] counts = {memberRepository.count(),todoRepository.count()};
        return counts;
    }



    /* 회원 아이디 중복 체크 */
    private void validateDuplicate(String username){
        Optional<Member> memberByUsername = memberRepository.findByUsername(username);

        memberByUsername.ifPresent(t -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
