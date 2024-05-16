package jpabook.jpashop.config.hanlder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
        System.out.println(" @@@@@@@@@@@@@@@@@@@@ 인증 성공 핸들러인데 !!! 실행 돼나 ? ");
        String previousUrl = (String) request.getSession().getAttribute("previousPageUrl");

        if(previousUrl!=null){
            System.out.println(" 여기가 실행돼야해 !!! " + previousUrl);
            response.sendRedirect(previousUrl);
        }else{
            response.sendRedirect("/todo");
        }



    }
}
