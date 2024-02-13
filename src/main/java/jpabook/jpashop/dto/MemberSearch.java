package jpabook.jpashop.dto;

import lombok.Data;

@Data
public class MemberSearch {
    private String username;
    private String provider;
    private String role;
}
