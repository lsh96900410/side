package jpabook.jpashop.domain.alarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm,Long> {

//    @Query(value = "select * from alarm where toMemberId = ?1 order by createDate desc limit  0, 5;", nativeQuery = true)
//    List<Alarm> mNotiForHeader(int loginUserId);

    // 최근 5개 알림만 나오도록 기능구현
    List<Alarm> findTop5ByToMemberIdOrderByCreateDateDesc(Long toMemberId);

    Optional<Alarm> findByToMemberIdAndFromMemberIdAndAlarmType(Long toMemberId,Long fromMemberId,AlarmType alarmType);

}
