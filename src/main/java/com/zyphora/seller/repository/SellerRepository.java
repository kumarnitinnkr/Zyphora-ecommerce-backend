package com.zyphora.seller.repository;

import com.zyphora.seller.entity.SellerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerProfile, Long> {

    Optional<SellerProfile> findByUserEmail(String email);
}