package com.zyphora.seller.entity;

import com.zyphora.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="seller_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private User user;

    private String businessName;
    private String gstNumber;
    private String mobile;
    private String address;

    @Enumerated(EnumType.STRING)
    private SellerStatus status;
}