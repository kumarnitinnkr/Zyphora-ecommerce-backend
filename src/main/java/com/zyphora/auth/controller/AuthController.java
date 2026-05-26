package com.zyphora.auth.controller;

import com.zyphora.auth.dto.*;
import com.zyphora.auth.entity.Role;
import com.zyphora.auth.entity.User;
import com.zyphora.auth.repository.UserRepository;
import com.zyphora.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * GET /api/v1/auth/me
     * Returns the currently logged-in user's profile including role.
     * Used by the admin panel to verify role after login.
     */
    @GetMapping("/me")
    public Object me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return Map.of(
                "id",       user.getId(),
                "fullName", user.getFullName() != null ? user.getFullName() : "",
                "email",    user.getEmail(),
                "role",     user.getRole().name()   // "ROLE_ADMIN" | "ROLE_SELLER" | "ROLE_USER"
        );
    }

    /**
     * POST /api/v1/auth/seed-admin
     * One-time endpoint to create the first admin account.
     * DISABLE THIS IN PRODUCTION after first use (or add a secret header guard).
     *
     * Body: { "email": "admin@zyphora.com", "password": "admin123", "fullName": "Super Admin" }
     */
    @PostMapping("/seed-admin")
    public Object seedAdmin(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return Map.of("message", "Admin already exists with this email");
        }
        User admin = User.builder()
                .fullName(request.getFullName() != null ? request.getFullName() : "Super Admin")
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();
        userRepository.save(admin);
        return Map.of("message", "Admin created successfully. Login at the admin panel.");
    }
}
