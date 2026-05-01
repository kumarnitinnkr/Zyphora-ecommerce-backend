package com.zyphora.cart.controller;

import com.zyphora.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping("/add")
    public Object add(@RequestParam Long productId,
                      @RequestParam Integer qty) {
        return service.add(productId, qty);
    }

    @GetMapping
    public Object all() {
        return service.all();
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable Long id,
                         @RequestParam Integer qty) {
        return service.update(id, qty);
    }

    @DeleteMapping("/{id}")
    public Object remove(@PathVariable Long id) {
        return service.remove(id);
    }

    @GetMapping("/checkout")
    public Object checkout() {
        return service.checkout();
    }
}