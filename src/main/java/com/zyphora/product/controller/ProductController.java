package com.zyphora.product.controller;

import com.zyphora.product.dto.ProductRequest;
import com.zyphora.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public Object add(@RequestBody ProductRequest request) {
        return service.add(request);
    }

    @GetMapping
    public Object all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.all(page, size);
    }

    @GetMapping("/{id}")
    public Object one(@PathVariable Long id) {
        return service.one(id);
    }

    @GetMapping("/search")
    public Object search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.search(keyword, page, size);
    }

    @GetMapping("/category/{id}")
    public Object category(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.byCategory(id, page, size);
    }
}