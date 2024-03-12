package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.dto.MemberSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em ;

    // 1. 회원 가입
    public void save(Member member){ em.persist(member); }

    // 2. 회원 목록
    public List<Member> findAll(){
        // jpql : 엔티티 대상 쿼리 vs SQL : 테이블 대상 쿼리
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }

    // 3. 회원 조회
    public Member findOne(Long id){ return em.find( Member.class , id); }

    // 4. 회원 이름으로 조회
    public Member findByName(String username){
        try {
            return em.createQuery("select m from Member m where m.username= :username", Member.class)
                    .setParameter("username", username).getSingleResult();
        }catch (NoResultException e ){
            return null;
        }
        }
        
    // 5. 동적 쿼리 회원 이름 및 등급
    public List<Member> searchQuery(MemberSearch memberSearch){
        JPAQuery<Member> query = new JPAQuery<>(em);
        QMember qMember = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();

        // 1. 소셜 로그인 별 검색
        if(StringUtils.hasText(memberSearch.getProvider())){
            builder.and(qMember.provider.eq(memberSearch.getProvider()));
        }
        // 2. 멤버 등급 별 검색
        if(StringUtils.hasText(memberSearch.getRole())){
            builder.and(qMember.role.eq(memberSearch.getRole()));
        }
        // 3. 멤버 이름 및 주소로 검색 [ 포함 ]
//        if(StringUtils.hasText(memberSearch.getUsername())){
//            builder.and(qMember.username.contains(memberSearch.getUsername()));
//        }
        if(StringUtils.hasText(memberSearch.getSearchType())){
            if(memberSearch.getSearchType().equals("주소")){
                builder.or(qMember.address.city.contains(memberSearch.getSearchText()))
                        .or(qMember.address.street.contains(memberSearch.getSearchText()))
                        .or(qMember.address.zipcode.contains(memberSearch.getSearchText()));
            }else{
                builder.and(qMember.username.contains(memberSearch.getSearchText()));
            }
        }

        return query.from(qMember).where(builder).fetch();
    }

}
