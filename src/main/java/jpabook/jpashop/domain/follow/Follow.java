package jpabook.jpashop.domain.follow;

import jakarta.persistence.*;
import jpabook.jpashop.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Member following;

    private LocalDateTime createdAt;


    public Follow(Member follower,Member following){
        this.follower=follower;
        this.following=following;
    }

}
