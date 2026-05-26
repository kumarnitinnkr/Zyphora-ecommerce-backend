package com.zyphora.user.controller;

import com.zyphora.auth.entity.User;
import com.zyphora.auth.repository.UserRepository;
import com.zyphora.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final JwtService     jwtService;

    // ── GET logged-in user's profile ──────────────────────────────
    // GET /api/v1/user/me
    @GetMapping("/me")
    public Map<String, Object> getMe(
            @RequestHeader("Authorization") String authHeader) {

        String email = extractEmail(authHeader);
        User user    = userRepository.findByEmail(email).orElseThrow();

        return Map.of(
            "id",       user.getId(),
            "fullName", user.getFullName() != null ? user.getFullName() : "",
            "email",    user.getEmail(),
            "role",     user.getRole() != null ? user.getRole().name() : "ROLE_USER",
            "profilePic", user.getProfilePic() != null ? user.getProfilePic() : ""
        );
    }

    // ── UPDATE logged-in user's profile ───────────────────────────
    // PUT /api/v1/user/update
    @PutMapping("/update")
    public Map<String, Object> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> body) {

        String email = extractEmail(authHeader);
        User user    = userRepository.findByEmail(email).orElseThrow();

        if (body.containsKey("fullName")   && !body.get("fullName").isBlank())
            user.setFullName(body.get("fullName"));

        if (body.containsKey("profilePic") && !body.get("profilePic").isBlank())
            user.setProfilePic(body.get("profilePic"));

        userRepository.save(user);

        return Map.of(
            "id",         user.getId(),
            "fullName",   user.getFullName() != null ? user.getFullName() : "",
            "email",      user.getEmail(),
            "profilePic", user.getProfilePic() != null ? user.getProfilePic() : ""
        );
    }

    // ── helper ────────────────────────────────────────────────────
    private String extractEmail(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        return jwtService.extractEmail(token);
    }
}
