package jpabook.jpashop.repository;

import jpabook.jpashop.domain.alarm.AlarmRepository;
import jpabook.jpashop.domain.follow.FollowRepository;
import jpabook.jpashop.domain.keyword.KeywordRepository;
import jpabook.jpashop.domain.like.LikeRepository;
import jpabook.jpashop.domain.member.MemberRepository;
import jpabook.jpashop.domain.todo.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/* Save Query 성능 개선은 어떻게 할것인가? Update 는? delete 는? 체크 */
@SpringBootTest
public class QueryIndexTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    KeywordRepository keywordRepository;
    /* Not */
    @Test
    void memberNot(){
        /* 1. existByUsername -> count Query 변경 하기 */

        /* 2. 꼭 Entity 반환이 필요한지 Checking + 포지션 Index 걸기 */
        //memberRepository.findTop5ByDeveloperPosition("백엔드");

        /* 3. Top6 멤버 -> Redis 뒤적일 수 있을 듯 ? , All fileSort */
        //memberRepository.findTop6ByOrderByTotalViewCountDesc();

        /* 4. 좋아요 중복 체크 조회 const,const,ref 사용 , left join 2번 사용*/
        //likeRepository.findByMemberIdAndTodoId(1L, 1L);

        /* 5. 알람 삭제 기능 const,const,ref 사용, left join 2번 사용 */
        //alarmRepository.findByToMemberIdAndFromMemberIdAndAlarmType(1L, 1L, AlarmType.LIKE);

        /* 6. 키워드 이름 존재 체크  */
        //keywordRepository.findByName("자바");

        /* 7. 피드 페이징 작업 index,index 사용.. viewCount 인덱싱 걸려있음 ;;; */
        //Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"viewCount"));
        //todoRepository.findAll(pageable);

        /* 8. 탑 투두 조회 = All 사용 */
        //List<Todo> top5Todos = todoRepository.findTop5ByOrderByViewCountDesc();


    }


    /* Ok */
    @Test
    void memberIndexing(){
        /* index 사용 */
        memberRepository.count();

        /* 회원 투투 조회 = Const + ref 사용 근데 left Join 사용함*/
        todoRepository.findByMemberIdOrderByCreateDateDesc(1L);

        /* 팔로워, 팔로잉 수 = const,ref , left Join */
        followRepository.countByFollowerId(1L);

        /* 팔로우 존재 체크 = const,const,ref, left Join */
        followRepository.findByFollowingIdAndFollowerId(1L,2L);

        /* 알람 서치 = const,ref 사용 근데 left Join 사용함 */
        alarmRepository.findTop5ByToMemberIdOrderByCreateDateDesc(1L);
    }



}
