package com.malgn.malgncms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * packageName    : com.malgn.malgncms.config
 * fileName       : SecurityConfig
 * author         : JAEIK
 * date           : 3/7/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/7/26        JAEIK       최초 생성
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf((csrf) -> csrf.disable()) // JWT 방식으로 인해 세션로그인으로할땐 활성화해야함
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/auth/**","/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/api/contests/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // JWT 방식  세션방식은 ALWAYS
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
