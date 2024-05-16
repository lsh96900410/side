package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.todo.QTodo;
import jpabook.jpashop.domain.todo.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SearchTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Test
    public void getRsult(){
        QTodo todo= QTodo.todo;
        List<Todo> fetch = jpaQueryFactory.selectFrom(todo).where(todo.title.contains("테스트")).fetch();

        System.out.println(fetch.size());
    }

}
