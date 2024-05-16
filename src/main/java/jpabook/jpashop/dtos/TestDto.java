package jpabook.jpashop.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TestDto {

    private int id = 1;
    private String name = "테스트중 이름 ";
    private String website = " 테스트중 웹사이트";
    private String bio = " 테스트 중 bio ";
    private String profileImage = " 테스트 중 이미지";
}
