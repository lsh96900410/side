//package jpabook.jpashop.config.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.nimbusds.jose.proc.SecurityContext;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jpabook.jpashop.config.auth.PrincipalDetails;
//import jpabook.jpashop.domain.Member;
//import jpabook.jpashop.repository.MemberRepository;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import java.io.IOException;
//
//
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//    private final String key= System.getenv("secretKey");
//    private MemberRepository memberRepository;
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,MemberRepository memberRepository) {
//        super(authenticationManager);
//        this.memberRepository=memberRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String checkJwt =request.getHeader("Authorization");
//        if(checkJwt==null || !checkJwt.startsWith("bearer ")){
//            chain.doFilter(request,response);
//        }
//
//        String jwt = checkJwt.replace("bearer ","");
//        String username = JWT.require(Algorithm.HMAC512(key)).build().verify(jwt).getClaim("username").asString();
//
//        if(username!=null){
//            Member findMember = memberRepository.findByName(username);
//            PrincipalDetails principalDetails =new PrincipalDetails(findMember);
//            Authentication authentication= new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request,response);
//    }
//}
