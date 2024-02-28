package jpabook.jpashop.dto;

import lombok.Data;

@Data
public class OAuthDto {

    private String eamil;
    private String password;
    private String provider;
}
