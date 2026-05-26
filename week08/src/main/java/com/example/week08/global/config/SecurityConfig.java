package com.example.week08.global.config;

import com.example.week08.global.security.exception.CustomAccessDenied;
import com.example.week08.global.security.exception.CustomEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * [미션2] Spring Security 설정
 * - Public API : /auth/** (회원가입 등) → 인증 없이 접근 가능
 * - Private API: 그 외 모든 엔드포인트 → 로그인 필요
 * - 인증 실패(401) → CustomEntryPoint, 인가 실패(403) → CustomAccessDenied 로 응답 통일
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // [미션2] Public API: 인증 없이 접근 가능
    private final String[] allowUris = {
            "/auth/**",              // 회원가입 등 인증 불필요 엔드포인트
            "/swagger-ui/**",        // Swagger UI
            "/swagger-resources/**",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)   // HTTP Basic 인증 비활성화
                .formLogin(AbstractHttpConfigurer::disable)   // 폼 로그인 비활성화 (REST API이므로)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()  // Public API
                        .anyRequest().authenticated()            // Private API
                )
                // 인증/인가 실패 시 응답 통일
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customEntryPoint())  // 401: 미인증
                        .accessDeniedHandler(customAccessDenied())     // 403: 권한 없음
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAccessDenied customAccessDenied() {
        return new CustomAccessDenied();
    }

    @Bean
    public CustomEntryPoint customEntryPoint() {
        return new CustomEntryPoint();
    }
}
