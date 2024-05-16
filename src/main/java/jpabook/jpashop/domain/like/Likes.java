package jpabook.jpashop.domain.like;

import jakarta.persistence.*;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.todo.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="todo_id")
    @Setter()
    private Todo todo;

    @CreationTimestamp
    private LocalDateTime createAt;


    public Likes(Member member,Todo todo){
        this.member=member;
        this.todo=todo;
    }

}
