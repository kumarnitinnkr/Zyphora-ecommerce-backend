package com.zyphora.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;

    private Integer stock;

    private String imageUrl;

    private Double rating;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}