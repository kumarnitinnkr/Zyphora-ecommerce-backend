package com.zyphora.admin.controller;

import com.zyphora.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @GetMapping("/dashboard")
    public Object dashboard() {
        return service.dashboard();
    }

    @GetMapping("/users")
    public Object users() {
        return service.users();
    }

    @GetMapping("/sellers")
    public Object sellers() {
        return service.sellers();
    }

    @PutMapping("/seller/{id}/approve")
    public Object approve(@PathVariable Long id) {
        return service.approveSeller(id);
    }
}