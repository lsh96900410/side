package jpabook.jpashop.domain.todo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static jpabook.jpashop.domain.keyword.QKeyword.*;
import static jpabook.jpashop.domain.todo.QTodo.*;
import static jpabook.jpashop.domain.member.QMember.*;

import jpabook.jpashop.dtos.FeedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<FeedDto> feedFindAll(Pageable pageable) {
        System.out.println("-----------------------------------------------------------------");
        long startTest2 = System.currentTimeMillis();
        List<Long> ids = jpaQueryFactory.select(todo.id).from(todo).orderBy(todo.viewCount.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber() * pageable.getPageSize()).fetch();

        long endTest2 = System.currentTimeMillis();
        System.out.println(" ============================= 1번 쿼리에요 속도는 : " +(endTest2-startTest2) +" ms");
        System.out.println("-----------------------------------------------------------------");

        System.out.println("-----------------------------------------------------------------");
        long startTest1 = System.currentTimeMillis();

        List<FeedDto> feedData = jpaQueryFactory.select(Projections.constructor(FeedDto.class,
                        todo.id, todo.title, todo.viewCount, todo.likeCount,
                        keyword.name, member.id, member.fileLocation))
                .from(todo)
                .join(todo.keyword, keyword)
                .join(todo.member, member)
                .where(todo.id.in(ids))
                .fetch();
        long endTest1= System.currentTimeMillis();
        System.out.println(" ============================= 1번 쿼리에요 속도는 : " +(endTest1-startTest1) +" ms");
        System.out.println("-----------------------------------------------------------------");

        System.out.println("-----------------------------------------------------------------");
        long startTest3 = System.currentTimeMillis();
        Long count = jpaQueryFactory.select(todo.count()).from(todo).fetchOne();
        long endTest3= System.currentTimeMillis();
        System.out.println(" ============================= 1번 쿼리에요 속도는 : " +(endTest3-startTest3) +" ms");
        System.out.println("-----------------------------------------------------------------");

        return PageableExecutionUtils.getPage(feedData, pageable, () -> count);

    }

    public Page<FeedDto> test1(Pageable pageable) {

//        JPAQuery<Long> ids = jpaQueryFactory.select(todo.id).from(todo).where(todo.id.gt(0)).orderBy(todo.viewCount.desc())
//                .limit(pageable.getPageSize())
//                .offset(pageable.getPageNumber() * pageable.getPageSize());


        List<FeedDto> test = jpaQueryFactory.select(Projections.constructor(FeedDto.class,
                        todo.id, todo.title, todo.viewCount, todo.likeCount,
                        keyword.name, member.id, member.fileLocation))
                .from(todo)
                .join(todo.keyword, keyword)
                .join(todo.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> todoCount = jpaQueryFactory.select(todo.count()).from(todo);


        return PageableExecutionUtils.getPage(test, pageable, () -> todoCount.fetchOne());

    }
}
