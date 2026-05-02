package com.zyphora.order.repository;

import com.zyphora.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserEmailOrderByIdDesc(String email);
}