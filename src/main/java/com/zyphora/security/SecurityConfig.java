package com.zyphora.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Public APIs
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // ✅ ADD THIS (categories public)
                .requestMatchers("/api/v1/categories/**").permitAll()

                // ✅ ADD THIS (secure product creation)
                .requestMatchers(HttpMethod.POST, "/api/v1/products")
                .hasAnyRole("ADMIN","SELLER")

                // Role based
                .requestMatchers("/api/v1/admin/**")
                    .hasRole("ADMIN")

                .requestMatchers("/api/v1/seller/**")
                    .hasRole("SELLER")

                .requestMatchers("/api/v1/user/**")
                    .hasAnyRole("USER","ADMIN","SELLER")

                .anyRequest().authenticated()
            )

            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}