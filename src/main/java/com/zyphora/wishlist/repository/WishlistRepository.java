package com.zyphora.wishlist.repository;

import com.zyphora.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUserEmail(String email);

    Optional<WishlistItem> findByUserEmailAndProductId(String email, Long productId);
}