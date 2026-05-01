package com.zyphora.wishlist.controller;

import com.zyphora.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService service;

    @PostMapping("/add")
    public Object add(@RequestParam Long productId) {
        return service.add(productId);
    }

    @GetMapping
    public Object all() {
        return service.all();
    }

    @DeleteMapping("/{id}")
    public Object remove(@PathVariable Long id) {
        return service.remove(id);
    }
}