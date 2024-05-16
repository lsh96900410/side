package jpabook.jpashop.config.hanlder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpabook.jpashop.constant.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error(" 가입되지 않은 회원입니다. ");

        String t = request.getRequestURI();
        System.out.println(" 우우웅웅" + t);

        request.getSession().setAttribute("errorMessage", ExceptionType.NOT_AUTHENTICATION_MEMBER.getMessage());
        request.getSession().setAttribute("previousPageUrl",t);


        response.sendRedirect("/members/sign");
    }
}
