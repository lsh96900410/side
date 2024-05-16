package jpabook.jpashop.domain.todo;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dtos.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static jpabook.jpashop.domain.todo.QTodo.todo;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Todo> searchTodoByTitle(RequestDto.Search search) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.hasText(search.getTitle())){
            booleanBuilder.and(todo.title.contains(search.getTitle()));
        }
        if(StringUtils.hasText(search.getKeyword())){
            booleanBuilder.and(todo.keyword.name.contains(search.getKeyword()));
        }
        if(StringUtils.hasText(search.getPosition())){
            booleanBuilder.and(todo.member.developerPosition.contains(search.getPosition()));
        }
        if( StringUtils.hasText(search.getCareer())){
            booleanBuilder.and(todo.member.career.contains(search.getCareer()));
        }


        List<Todo> searchResult = jpaQueryFactory.selectFrom(todo).where(booleanBuilder).fetch();

        return searchResult;
    }
}
