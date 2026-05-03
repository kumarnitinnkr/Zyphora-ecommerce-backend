package com.zyphora.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    @PostMapping("/apply")
    public Object apply(
            @RequestParam String code,
            @RequestParam Double amount){

        if(code.equalsIgnoreCase("SAVE10")){
            double finalAmount = amount - (amount * 0.10);

            return java.util.Map.of(
                    "discount","10%",
                    "finalAmount",finalAmount
            );
        }

        return "Invalid Coupon";
    }
}