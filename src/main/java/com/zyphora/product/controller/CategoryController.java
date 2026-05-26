package com.zyphora.product.controller;

import com.zyphora.product.entity.Category;
import com.zyphora.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repository;

    /**
     * GET /api/v1/categories
     * Public — returns all categories (used by Flutter app + admin panel).
     */
    @GetMapping
    public Object all() {
        return repository.findAll();
    }

    /**
     * POST /api/v1/categories
     * ADMIN only — add a new category.
     * Body: { "name": "Electronics", "imageUrl": "https://..." }
     */
    @PostMapping
    public Object add(@RequestBody Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            return Map.of("error", "Category name is required");
        }
        return repository.save(category);
    }

    /**
     * DELETE /api/v1/categories/{id}
     * ADMIN only — delete a category.
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return Map.of("error", "Category not found");
        }
        repository.deleteById(id);
        return Map.of("message", "Category deleted successfully");
    }

    /**
     * PUT /api/v1/categories/{id}
     * ADMIN only — update a category.
     */
    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @RequestBody Category updated) {
        return repository.findById(id).map(cat -> {
            if (updated.getName() != null) cat.setName(updated.getName());
            if (updated.getImageUrl() != null) cat.setImageUrl(updated.getImageUrl());
            return repository.save(cat);
        }).orElse(null);
    }
}
