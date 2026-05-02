package com.zyphora.order.controller;

import com.zyphora.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/place")
    public Object place(
            @RequestParam Long addressId,
            @RequestParam String paymentMethod) {

        return service.placeOrder(addressId, paymentMethod);
    }

    @GetMapping
    public Object history() {
        return service.history();
    }
}