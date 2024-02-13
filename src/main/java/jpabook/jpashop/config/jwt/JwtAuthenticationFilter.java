//package jpabook.jpashop.config.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jpabook.jpashop.config.auth.PrincipalDetails;
//import jpabook.jpashop.domain.Member;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.Date;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//
//
//    @Override
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println("실행돼........................?? ");
//        try {
//
//            ObjectMapper om = new ObjectMapper();
//            Member member = om.readValue(request.getInputStream(),Member.class);
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword());
//
//
//            Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//            PrincipalDetails pd =(PrincipalDetails) authentication.getPrincipal();
//
//            return authentication;
//        } catch (IOException e) {
//            e.getMessage();
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain
//            , Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
//        PrincipalDetails principalDetails =(PrincipalDetails) authResult.getPrincipal();
//
//        String secretKey = System.getenv("secretKey");
//
//        String jwtToken = JWT.create()
//                .withSubject("jpaPracticeToken")
//                .withClaim("username",principalDetails.getUsername())
//                .withClaim("role",principalDetails.getMember().getRole())
//                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
//                .sign(Algorithm.HMAC512(secretKey));
//
//        response.addHeader("Authorization","bearer "+jwtToken);
//    }
//}
