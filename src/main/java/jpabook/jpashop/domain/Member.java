package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
/* TO DO */
// 1. orders 필드 삭제
// 2. 스프링 시큐리티 적용 위해서 역할 타입 추가
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    private String password;

    @Embedded
    private Address address;

    /* 삭제 보류  */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
