package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberForm;
import jpabook.jpashop.dto.MemberLogin;
import jpabook.jpashop.dto.MemberSearch;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     *  회원 가입
     */
    @Transactional 
    public Long join(MemberForm form){
        validateDuplicateMember(form.getUsername());
        form.setPassword(encoder.encode(form.getPassword()));
        Member saveMember = new Member(form);
        memberRepository.save(saveMember);

        return saveMember.getId();
    }

    // 2. 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 3. 회원 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    // 4. 로그인
    public void login(MemberLogin memberLogin) throws Exception{
        Member findMember = memberRepository.findByName(memberLogin.getUsername());

            if (findMember==null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + memberLogin.getUsername());
            }
            if(!encoder.matches(memberLogin.getPassword(),findMember.getPassword())){
                throw new BadCredentialsException("비밀번호가 틀렸습니다.");
            }
    }

    // 검색
    public List<Member> searchMembers(MemberSearch memberSearch){
        return memberRepository.searchQuery(memberSearch);
    }


    // 중복 회원 검증
    private void validateDuplicateMember(String name) {
        Member findMembers = memberRepository.findByName(name);
        if(findMembers!=null){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


}
