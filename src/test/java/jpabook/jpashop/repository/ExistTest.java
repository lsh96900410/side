package jpabook.jpashop.repository;

import jpabook.jpashop.domain.member.ExistQuery;
import jpabook.jpashop.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExistTest {

    @Autowired
    private ExistQuery existQuery;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void existTest(){
        long startTime = System.currentTimeMillis();
        existQuery.exist("105gh");
        long endTime = System.currentTimeMillis();
        System.out.println("시간은 " + (endTime-startTime) + "ms");
    }

    @Test
    void repoTest(){
        long startTime = System.currentTimeMillis();
        memberRepository.existsByUsername("105gh");
        long endTime = System.currentTimeMillis();
        System.out.println("시간은 " + (endTime-startTime) + "ms");
    }
}
