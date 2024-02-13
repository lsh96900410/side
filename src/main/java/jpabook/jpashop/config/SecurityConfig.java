package jpabook.jpashop.config;

import jpabook.jpashop.config.auth.PrincipalDetailsService;
import jpabook.jpashop.config.oauth.PrincipalOauth2UserService;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);

//            http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        //접근 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/members","/orders/{orderId}/cancel","/orders").hasAnyRole("ADMIN","MANAGER")
                .anyRequest().permitAll());
        // 로그인 설정
        http.formLogin(customizer -> customizer
                .loginPage("/members/login").loginProcessingUrl("/members/login")
                        .defaultSuccessUrl("/").failureForwardUrl("/members/login"));
//                .httpBasic(httpBasicConfigurer->httpBasicConfigurer.disable()); //bearer
        // 소셜 로그인 설정
        http.oauth2Login(oauth2Customizer ->
                oauth2Customizer.loginPage("/members/login")
                        .userInfoEndpoint(userInfoEndpointCustomize->
                                userInfoEndpointCustomize.userService(principalOauth2UserService)));
        return http.build();
    }

}
