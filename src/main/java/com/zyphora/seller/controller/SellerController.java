package com.zyphora.seller.controller;

import com.zyphora.seller.entity.SellerProfile;
import com.zyphora.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService service;

    @PostMapping("/apply")
    public Object apply(@RequestBody SellerProfile request) {
        return service.apply(request);
    }

    @GetMapping("/me")
    public Object myProfile() {
        return service.myProfile();
    }
}