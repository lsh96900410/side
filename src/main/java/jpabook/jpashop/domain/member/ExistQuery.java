package jpabook.jpashop.domain.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static jpabook.jpashop.domain.member.QMember.*;

@Repository
@RequiredArgsConstructor
public class ExistQuery {

    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public Boolean exist(String username){
        Integer fetchOne = queryFactory.selectOne().from(member).where(member.username.eq(username)).fetchFirst();

        return fetchOne != null;
    }
}
