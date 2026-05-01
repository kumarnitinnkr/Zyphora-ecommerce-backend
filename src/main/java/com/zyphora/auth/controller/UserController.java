package com.zyphora.user.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/profile")
    public String profile() {
        return "User secured API working";
    }
}