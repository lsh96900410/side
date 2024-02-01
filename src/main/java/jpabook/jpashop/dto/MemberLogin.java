package jpabook.jpashop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLogin {
    @NotBlank(message = "아이디를 다시 입력하세요")
    private String username;
    @NotBlank(message = "비빌먼호를 다시 입력하세요")
    private String password;
}
