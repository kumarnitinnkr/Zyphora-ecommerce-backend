package com.zyphora.product.service;

import com.zyphora.product.dto.ProductRequest;
import com.zyphora.product.entity.*;
import com.zyphora.product.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product add(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .rating(0.0)
                .active(true)
                .category(category)
                .build();

        return productRepository.save(product);
    }

    public Page<Product> all(int page, int size) {
        return productRepository.findAll(
                PageRequest.of(page, size, Sort.by("id").descending())
        );
    }

    public Product one(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Page<Product> search(String keyword, int page, int size) {
        return productRepository.findByTitleContainingIgnoreCase(
                keyword,
                PageRequest.of(page, size)
        );
    }

    public Page<Product> byCategory(Long categoryId, int page, int size) {
        return productRepository.findByCategoryId(
                categoryId,
                PageRequest.of(page, size)
        );
    }
}