package jpabook.jpashop.domain.todo;

import jakarta.persistence.*;
import jpabook.jpashop.domain.keyword.Keyword;
import jpabook.jpashop.domain.like.Likes;
import jpabook.jpashop.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @Column(name="todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private boolean completionStatus;

    private int viewCount ;

    @CreationTimestamp
    private LocalDateTime createDate;

    //,cascade = CascadeType.ALL,orphanRemoval = true
    @OneToMany(mappedBy = "todo")
    private List<Likes> likes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="keyword_id")
    private Keyword keyword;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    public Todo(String title,Keyword keyword,Member member){
        this.title=title;
        this.keyword=keyword;
        this.member=member;
    }

    /* 생성 메서드 */

//    private void addTodoHashtags(TodoKeyword todoKeyword){
//        todoKeyword.setTodo(this);
//        if(todoKeyword.getKeyword()!=null){
//            todoKeywords.add(todoKeyword);
//        }
//    }

    public void upViewCount(){
        System.out.println("upViewCount 로직 전 : "+this.viewCount );
        this.viewCount++;
        System.out.println("upViewCount 로직 후 : "+this.viewCount );

    }
    /* 비즈니스 메서드 */

    public void todoComplete(){
        completionStatus=true;
    }

    public void createTodoPost(String content) {
        this.content=content;
    }
}
