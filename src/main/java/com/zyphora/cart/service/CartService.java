package com.zyphora.cart.service;

import com.zyphora.auth.repository.UserRepository;
import com.zyphora.cart.entity.CartItem;
import com.zyphora.cart.repository.CartRepository;
import com.zyphora.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private String email() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    public Object add(Long productId, Integer qty) {

        var existing = cartRepository
                .findByUserEmailAndProductId(email(), productId);

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + qty);
            return cartRepository.save(item);
        }

        CartItem item = CartItem.builder()
                .user(userRepository.findByEmail(email()).orElseThrow())
                .product(productRepository.findById(productId).orElseThrow())
                .quantity(qty)
                .build();

        return cartRepository.save(item);
    }

    public Object all() {
        return cartRepository.findByUserEmail(email());
    }

    public Object update(Long id, Integer qty) {
        CartItem item = cartRepository.findById(id).orElseThrow();
        item.setQuantity(qty);
        return cartRepository.save(item);
    }

    public Object remove(Long id) {
        cartRepository.deleteById(id);
        return "Removed";
    }

    public Object checkout() {

        var items = cartRepository.findByUserEmail(email());

        double total = 0;

        for (CartItem item : items) {
            total += item.getProduct().getPrice().doubleValue()
                    * item.getQuantity();
        }

        return java.util.Map.of(
                "items", items,
                "total", total,
                "delivery", 50,
                "grandTotal", total + 50
        );
    }
}