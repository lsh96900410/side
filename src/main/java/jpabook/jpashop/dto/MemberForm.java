package jpabook.jpashop.dto;

import jakarta.validation.constraints.*;
import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String username;


    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,10}"
            , message = "비밀번호는 4~10자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @Email(message = "이메일 형식을 맞춰주세요 ")
    private String email;

    private String city;
    private String street;
    private String zipcode;

    public Address formToAddress(){
        return new Address(city,street,zipcode);
    }

}
