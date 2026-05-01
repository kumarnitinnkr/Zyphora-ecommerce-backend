package com.zyphora.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    private String title;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private Long categoryId;
}