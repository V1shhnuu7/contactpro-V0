package com.contactpro.contactpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Temporary security config
 * Allows all requests without authentication
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())  // disable CSRF for testing
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // allow all requests
                );

        return http.build();
    }
}