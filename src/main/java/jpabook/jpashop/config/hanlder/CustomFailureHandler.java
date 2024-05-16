package jpabook.jpashop.config.hanlder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 여기는 스프링 시큐리티 필터야 !!!!!! ");
        Object loginForm = request.getAttribute("loginForm");


        if(loginForm!=null){
            System.out.println(" loginForm 데이타 들어옴");
        }
        if(exception instanceof BadCredentialsException){
            errorMessage="아이디/비밀번호 가 맞지 않습니다.";
        } else if(exception instanceof InternalAuthenticationServiceException){
            errorMessage="내부 시스템 오류입니다. 빠른 시간 안에 처리하겠습니다.";
        } else if(exception instanceof UsernameNotFoundException){
            errorMessage="아직 회원이 아닙니다. 회원가입 먼저 해주세요";
        } else if(exception instanceof AuthenticationCredentialsNotFoundException){
            errorMessage ="인증 요청이 거부되었습니다.";
        } else{
            errorMessage= "알 수 없는 에러입니다.";
        }
        errorMessage = URLEncoder.encode(errorMessage,"UTF-8");
        setDefaultFailureUrl("/members/login?error=true&exception="+errorMessage);
        super.onAuthenticationFailure(request,response,exception);
    }
}
