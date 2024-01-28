package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    //== 동적 쿼리 ==//

    /**
     * 1. JPQL 쿼리를 조건문을 통해 문자로 생성
     */
    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    /**
     *  2. JPA Criteria --> JPA가 자바 코드로 JPQL을 작성할 수 있게 해주는 기능, JPA 표준 스펙이지만 실무 사용은 너무 복잡
     *  [ 비추 ]
     *  유지 보수성 거의 제로....
     *  상상이 안감 JPQL이 어떻게 생성 될 지
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        if(orderSearch.getOrderStatus() != null){
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        
        // 회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())){
            Predicate status =
                    cb.like(m.get("name"),"%"+orderSearch.getMemberName()+"%");
            criteria.add(status);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();

    }

    /**
     *  3. Querydsl
     */
    public List<Order> queryDSL(OrderSearch orderSearch){
        JPAQuery<Order> query = new JPAQuery(em); // JPAQuery 객체 생성 + EntityManager 넘기기
        QOrder qOrder =QOrder.order;
        QMember member = QMember.member;    // 조인 시 사용
        BooleanBuilder builder = new BooleanBuilder(); // where 조건문

        // 1. 주문 상태 검색
        if(orderSearch.getOrderStatus()!=null){
            builder.and(qOrder.status.eq(orderSearch.getOrderStatus()));
        }

        // 2. 회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())){
            builder.and(qOrder.member.name.eq(orderSearch.getMemberName()));
        }

        return query.from(qOrder).join(qOrder.member,member).where(builder).fetch();
    }
}
