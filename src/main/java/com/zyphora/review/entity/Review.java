package com.zyphora.review.entity;

import com.zyphora.auth.entity.User;
import com.zyphora.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private Integer rating;

    @Column(length = 2000)
    private String comment;
}