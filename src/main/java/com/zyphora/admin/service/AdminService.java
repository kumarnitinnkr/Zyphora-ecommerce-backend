package com.zyphora.admin.service;

import com.zyphora.auth.repository.UserRepository;
import com.zyphora.order.repository.OrderRepository;
import com.zyphora.product.repository.ProductRepository;
import com.zyphora.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final SellerService sellerService;

    public Object dashboard() {

        double revenue = orderRepository.findAll()
                .stream()
                .mapToDouble(o -> o.getTotalAmount().doubleValue())
                .sum();

        return java.util.Map.of(
                "users", userRepository.count(),
                "products", productRepository.count(),
                "orders", orderRepository.count(),
                "revenue", revenue
        );
    }

    public Object users() {
        return userRepository.findAll();
    }

    public Object sellers() {
        return sellerService.all();
    }

    public Object approveSeller(Long id) {
        return sellerService.approve(id);
    }
}