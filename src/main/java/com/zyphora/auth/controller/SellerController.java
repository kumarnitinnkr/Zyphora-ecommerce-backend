package com.zyphora.seller.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seller")

public class SellerController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Seller Dashboard Access Granted";
    }
}