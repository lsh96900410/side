package jpabook.jpashop.domain.alarm;

import jakarta.persistence.*;
import jpabook.jpashop.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Alarm {

    @Id
    @Column(name="alarm_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="fromMemberId")
    private Member fromMember;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    private boolean isRead=false;

    @ManyToOne
    @JoinColumn(name="toMemberId")
    private Member toMember;

    @CreationTimestamp
    private LocalDateTime createDate;

    public Alarm (Member fromMember,Member toMember,AlarmType type){
        this.fromMember=fromMember;
        this.toMember=toMember;
        this.alarmType=type;
    }


}
