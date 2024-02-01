package jpabook.jpashop.config;

import jpabook.jpashop.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/members","/orders/{orderId}/cancel","/orders").hasAnyRole("ADMIN","MANAGER")
                .anyRequest().permitAll());

        http.formLogin(customizer -> customizer
                .loginPage("/members/login").loginProcessingUrl("/members/login")
                        .defaultSuccessUrl("/").failureForwardUrl("/members/login"));

        http.oauth2Login(oauth2Customizer ->
                oauth2Customizer.loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointCustomize->
                                userInfoEndpointCustomize.userService(principalOauth2UserService)));
        return http.build();
    }
}
