package com.zyphora.wishlist.entity;

import com.zyphora.auth.entity.User;
import com.zyphora.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wishlist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Product product;
}