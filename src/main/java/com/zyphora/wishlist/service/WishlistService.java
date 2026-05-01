package com.zyphora.wishlist.service;

import com.zyphora.auth.repository.UserRepository;
import com.zyphora.product.repository.ProductRepository;
import com.zyphora.wishlist.entity.WishlistItem;
import com.zyphora.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private String email() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    public Object add(Long productId) {

        if (repository.findByUserEmailAndProductId(email(), productId).isPresent()) {
            return "Already added";
        }

        WishlistItem item = WishlistItem.builder()
                .user(userRepository.findByEmail(email()).orElseThrow())
                .product(productRepository.findById(productId).orElseThrow())
                .build();

        return repository.save(item);
    }

    public Object all() {
        return repository.findByUserEmail(email());
    }

    public Object remove(Long id) {
        repository.deleteById(id);
        return "Removed";
    }
}