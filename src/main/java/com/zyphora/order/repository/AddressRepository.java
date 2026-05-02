package com.zyphora.order.repository;

import com.zyphora.order.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserEmail(String email);
}