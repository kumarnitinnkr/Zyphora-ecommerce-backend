package com.zyphora.admin.service;

import com.zyphora.auth.repository.UserRepository;
import com.zyphora.order.entity.OrderEntity;
import com.zyphora.order.entity.OrderStatus;
import com.zyphora.order.repository.OrderRepository;
import com.zyphora.product.repository.ProductRepository;
import com.zyphora.seller.entity.SellerStatus;
import com.zyphora.seller.repository.SellerRepository;
import com.zyphora.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final SellerRepository sellerRepository;
    private final SellerService sellerService;

    public Object dashboard() {
        double revenue = orderRepository.findAll()
                .stream()
                .mapToDouble(o -> o.getTotalAmount() != null ? o.getTotalAmount().doubleValue() : 0)
                .sum();

        return Map.of(
                "users",    userRepository.count(),
                "products", productRepository.count(),
                "orders",   orderRepository.count(),
                "revenue",  revenue
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

    public Object rejectSeller(Long id) {
        var seller = sellerRepository.findById(id).orElseThrow();
        seller.setStatus(SellerStatus.REJECTED);
        sellerRepository.save(seller);
        return Map.of("message", "Seller rejected");
    }

    public Object allOrders() {
        return orderRepository.findAll();
    }

    public Object updateOrderStatus(Long id, String status) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Map.of("error", "Invalid status: " + status +
                    ". Valid values: PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED");
        }
        orderRepository.save(order);
        return Map.of("message", "Order #" + id + " updated to " + status);
    }
}
