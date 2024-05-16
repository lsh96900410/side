package jpabook.jpashop.config;

import jpabook.jpashop.config.hanlder.CustomAuthenticationEntryPoint;
import jpabook.jpashop.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFailureHandler customFailureHandler;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        //접근 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/todo/search").permitAll()
                .requestMatchers("/members/{memberId}","members/logout","/todo/{todoId}").authenticated()
                .anyRequest().permitAll());

//                .requestMatchers("/orders/{orderId}/cancel","/orders").hasAnyRole("ADMIN","MANAGER")



        // 로그인 설정
        http.formLogin(customizer -> customizer
                .loginPage("/members/login").permitAll().loginProcessingUrl("/members/login")
//                .successHandler(new CustomSuccessHandler())
                .failureHandler(customFailureHandler).failureUrl("/members/sign")
                .defaultSuccessUrl("/todo"));

        // 소셜 로그인 설정
        http.oauth2Login(oauth2Customizer ->
                oauth2Customizer.loginPage("/members/login")
                        .userInfoEndpoint(userInfoEndpointCustomize->
                                userInfoEndpointCustomize.userService(principalOauth2UserService)));
        
        // 권한 예외 설정
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {response.sendRedirect("/403.html");})

                        );

        return http.build();
    }

}
