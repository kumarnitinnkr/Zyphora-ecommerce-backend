package com.zyphora.product.controller;

import com.zyphora.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repository;

    @GetMapping
    public Object all() {
        return repository.findAll();
    }
}