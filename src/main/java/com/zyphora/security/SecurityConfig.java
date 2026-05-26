package com.zyphora.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth

                // ─── PUBLIC ────────────────────────────────────────────────────────
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // Allow CORS pre-flight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ─── PUBLIC READ ────────────────────────────────────────────────────
                .requestMatchers(HttpMethod.GET,  "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/v1/categories/**").permitAll()

                // ─── CATEGORY MANAGEMENT (ADMIN only) ──────────────────────────────
                .requestMatchers(HttpMethod.POST,   "/api/v1/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")

                // ─── PRODUCT MANAGEMENT ────────────────────────────────────────────
                .requestMatchers(HttpMethod.POST,   "/api/v1/products").hasAnyRole("ADMIN", "SELLER")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/products/**").hasAnyRole("ADMIN", "SELLER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAnyRole("ADMIN", "SELLER")

                // ─── SELLER ────────────────────────────────────────────────────────
                // Any authenticated user can apply to become a seller
                .requestMatchers("/api/v1/seller/apply").authenticated()
                // Only approved sellers can access their profile
                .requestMatchers("/api/v1/seller/me").hasRole("SELLER")

                // ─── ADMIN ─────────────────────────────────────────────────────────
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                // ─── USER ──────────────────────────────────────────────────────────
                .requestMatchers("/api/v1/user/**").permitAll()

                // ─── FALLBACK ──────────────────────────────────────────────────────
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
