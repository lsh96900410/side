package jpabook.jpashop.dto;

import jpabook.jpashop.domain.Address;
import lombok.Data;

@Data
public class MemberSearch {
    private String username;
    private String provider;
    private String role;
    private String searchType;
    private Address address;
    private String searchText;
}
