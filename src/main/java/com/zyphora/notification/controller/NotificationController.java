package com.zyphora.notification.controller;

import com.zyphora.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;

    private String email(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping
    public Object all(){
        return repository.findByUserEmail(email());
    }
}