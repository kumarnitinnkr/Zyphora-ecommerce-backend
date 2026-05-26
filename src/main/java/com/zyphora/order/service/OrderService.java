package com.zyphora.order.service;

import com.zyphora.auth.repository.UserRepository;
import com.zyphora.cart.repository.CartRepository;
import com.zyphora.notification.service.EmailNotificationService;
import com.zyphora.order.dto.OrderResponse;
import com.zyphora.order.entity.*;
import com.zyphora.order.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository          orderRepository;
    private final OrderItemRepository      orderItemRepository;
    private final CartRepository           cartRepository;
    private final AddressRepository        addressRepository;
    private final UserRepository           userRepository;
    private final PaymentService           paymentService;
    private final EmailNotificationService emailService;

    private String email() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    // ── Place Order ────────────────────────────────────────────────────────────
    public Object placeOrder(Long addressId, String paymentMethod) {

        var cart = cartRepository.findByUserEmail(email());

        double total = 0;
        for (var item : cart) {
            total += item.getProduct().getPrice().doubleValue() * item.getQuantity();
        }

        String paymentStatus = paymentMethod.equalsIgnoreCase("COD")
                ? paymentService.cod() : paymentService.online();

        OrderEntity order = OrderEntity.builder()
                .user(userRepository.findByEmail(email()).orElseThrow())
                .address(addressRepository.findById(addressId).orElseThrow())
                .totalAmount(BigDecimal.valueOf(total))
                .paymentMethod(paymentMethod)
                .paymentStatus(paymentStatus)
                .status(OrderStatus.PLACED)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        for (var item : cart) {
            orderItemRepository.save(
                    OrderItem.builder()
                            .order(order)
                            .product(item.getProduct())
                            .quantity(item.getQuantity())
                            .price(item.getProduct().getPrice())
                            .build()
            );
        }

        cartRepository.deleteAll(cart);

        try {
            emailService.sendOrderConfirmation(order);
        } catch (Exception e) {
            log.error("Notification error (order still placed): {}", e.getMessage());
        }

        // Return the full order response with items
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        return new OrderResponse(order, items);
    }

    // ── Order History ──────────────────────────────────────────────────────────
    /// Returns orders with full address + items — Flutter can parse directly.
    public Object history() {
        List<OrderEntity> orders =
                orderRepository.findByUserEmailOrderByIdDesc(email());

        return orders.stream()
                .map(order -> {
                    List<OrderItem> items =
                            orderItemRepository.findByOrderId(order.getId());
                    return new OrderResponse(order, items);
                })
                .collect(Collectors.toList());
    }
}
