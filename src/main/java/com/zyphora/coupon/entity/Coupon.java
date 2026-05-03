package com.zyphora.coupon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Double discountPercent;
    private Boolean active;
}