package com.zyphora.order.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public String cod() {
        return "PENDING";
    }

    public String online() {
        return "PAID";
    }
}