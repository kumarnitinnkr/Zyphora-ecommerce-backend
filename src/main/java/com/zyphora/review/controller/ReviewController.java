package com.zyphora.review.controller;

import com.zyphora.review.entity.Review;
import com.zyphora.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository repository;

    @PostMapping
    public Object add(@RequestBody Review review){
        return repository.save(review);
    }

    @GetMapping("/product/{id}")
    public Object list(@PathVariable Long id){
        return repository.findByProductId(id);
    }
}