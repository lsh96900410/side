package jpabook.jpashop.domain.keyword;

import jakarta.persistence.*;
import jpabook.jpashop.domain.todo.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Keyword {

    @Id
    @Column(name="keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "keyword",cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    public Keyword(String name){
        this.name=name;
    }


}
