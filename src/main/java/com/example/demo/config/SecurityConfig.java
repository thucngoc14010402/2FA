package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/2fa/**").permitAll() // Cho phép truy cập tất cả endpoint dưới /api/2fa/** mà không cần xác thực
                        .anyRequest().authenticated() // Các endpoint khác yêu cầu xác thực
                )
                .csrf((csrf) -> csrf.disable()); // Vô hiệu hóa CSRF (dành cho kiểm thử, bật lại trong production nếu cần)
        return http.build();
    }
}