package jpabook.jpashop.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class FormData {

    @Data
    public static class Login{

        @NotBlank(message = "아이디를 다시 입력하세요")
        private String username;
        @NotBlank(message = "비빌먼호를 다시 입력하세요")
        private String password;
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class Join{

        @NotBlank(message = " 아이디는 필수 입력값입니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,10}"
                , message = "4~10자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @Email(message = "이메일 형식을 맞춰주세요 ")
        private String email;

        @NotEmpty(message = " 회원님의 이름을 입력해주세요 ")
        private String name;

        private String developerPosition;

        @NotBlank(message = "경력 사항은 필수입니다.")
        private String career;
    }

}
