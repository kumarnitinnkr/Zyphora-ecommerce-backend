package com.zyphora.user.controller;

import com.zyphora.auth.entity.User;
import com.zyphora.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profile() {
        return "User secured API working";
    }

    @PutMapping("/update")
    public User updateProfile(
            @RequestBody User updatedUser) {

        User user = userRepository
                .findById(updatedUser.getId())
                .orElseThrow();

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }
}