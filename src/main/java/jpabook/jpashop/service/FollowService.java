package jpabook.jpashop.service;

import jpabook.jpashop.constant.ExceptionType;
import jpabook.jpashop.domain.alarm.Alarm;
import jpabook.jpashop.domain.alarm.AlarmRepository;
import jpabook.jpashop.domain.alarm.AlarmType;
import jpabook.jpashop.domain.follow.Follow;
import jpabook.jpashop.domain.follow.FollowRepository;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.MemberRepository;
import jpabook.jpashop.dtos.RequestDto;
import jpabook.jpashop.dtos.ResponseDto;
import jpabook.jpashop.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;

    /* 팔로우 서비스 */
    public void follow(RequestDto.FollowDto followRequest){

        if(followRequest.getFollowerId()==(followRequest.getFollowingId())){
            throw new CustomException(ExceptionType.SELF_FOLLOW);
        }

        Optional<Follow> checkExistFollow =
                followRepository.findByFollowingIdAndFollowerId(followRequest.getFollowingId(), followRequest.getFollowerId());

        checkExistFollow.ifPresent( existFollow -> { throw new CustomException(ExceptionType.EXIST_FOLLOW);});

        Member followerMember =
                memberRepository.findById(followRequest.getFollowerId())
                        .orElseThrow(()-> new CustomException(ExceptionType.NOT_EXIST_MEMBER));

        Member followingMember =
                memberRepository.findById(followRequest.getFollowingId())
                        .orElseThrow(()-> new CustomException(ExceptionType.NOT_EXIST_MEMBER));

        Follow follow = new Follow(followerMember,followingMember);

        followRepository.save(follow);

        /* 알람 설정 */

        Alarm alarm=new Alarm(followerMember,followingMember, AlarmType.FOLLOW);

        alarmRepository.save(alarm);

    }

    /* 언팔로우 */

    @Transactional
    public void unFollow(RequestDto.FollowDto unFollowRequest){

        Follow follow = followRepository.findByFollowingIdAndFollowerId(unFollowRequest.getFollowingId(),unFollowRequest.getFollowerId())
                .orElseThrow(() ->
                        new CustomException(ExceptionType.NOT_EXIST_FOLLOW));

        followRepository.delete(follow);

        /* 알람 삭제 */
        Alarm alarm = alarmRepository.findByToMemberIdAndFromMemberIdAndAlarmType(follow.getFollowing().getId(),follow.getFollower().getId(),AlarmType.FOLLOW)
                .orElseThrow(() ->
                        new CustomException(ExceptionType.NOT_EXIST_ALARM));

        alarmRepository.delete(alarm);

    }


    /* 팔로잉, 팔로워 보기 */
    public void getFollowers(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new CustomException(ExceptionType.NOT_EXIST_MEMBER));

        List<Follow> follows = member.getFollows();

        List<ResponseDto.Follows> resultFollowers =
                follows.stream().map(follower ->
                        new ResponseDto.Follows(follower)).collect(Collectors.toList());

    }

    public void getFollowings(Long memberId){

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ExceptionType.NOT_EXIST_MEMBER));

        List<Follow> followings = member.getFollowings();
        List<ResponseDto.Follows> resultFollowers =
                followings.stream().map(following ->
                        new ResponseDto.Follows(following)).collect(Collectors.toList());
    }
}
