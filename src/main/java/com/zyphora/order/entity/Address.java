package com.zyphora.order.entity;

import com.zyphora.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    private String fullName;
    private String mobile;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pincode;
    private String country;
}