package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        // em.flush();
        assertEquals(member,memberRepository.findOne(savedId));
        // JPA에서 같은 트랜잭션에서 엔티티들의 pk가 똑같으면 같은 객체로 관리
    }

    @Test()
    public void 중복회원예외() throws  Exception{
        //given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("kim1");
        member2.setName("kim1");
        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class,()->memberService.join(member2));

        //fail("예외 발생해야 함 ");
    }
}