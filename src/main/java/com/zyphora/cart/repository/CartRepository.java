package com.zyphora.cart.repository;

import com.zyphora.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserEmail(String email);

    Optional<CartItem> findByUserEmailAndProductId(String email, Long productId);
}