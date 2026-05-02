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
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
            // ❌ Disable CSRF (JWT based)
            .csrf(csrf -> csrf.disable())

            // ❌ Disable session (VERY IMPORTANT for JWT)
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 🌐 Enable CORS (important for Flutter / frontend)
            .cors(cors -> {})

            .authorizeHttpRequests(auth -> auth

                // ================= PUBLIC =================
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // Allow preflight (important for frontend)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ================= PUBLIC APIs =================
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()

                // ================= PRODUCT SECURITY =================
                .requestMatchers(HttpMethod.POST, "/api/v1/products")
                        .hasAnyRole("ADMIN", "SELLER")

                // ================= SELLER =================
                .requestMatchers("/api/v1/seller/apply")
                        .hasAnyRole("USER", "ADMIN")

                .requestMatchers("/api/v1/seller/me")
                        .hasRole("SELLER")

                // ================= ADMIN =================
                .requestMatchers("/api/v1/admin/**")
                        .hasRole("ADMIN")

                // ================= USER =================
                .requestMatchers("/api/v1/user/**")
                        .hasAnyRole("USER", "ADMIN", "SELLER")

                // ================= FALLBACK =================
                .anyRequest().authenticated()
            )

            // 🔐 JWT Filter
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