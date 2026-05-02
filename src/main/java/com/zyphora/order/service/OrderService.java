package com.zyphora.order.service;

import com.zyphora.auth.repository.UserRepository;
import com.zyphora.cart.repository.CartRepository;
import com.zyphora.order.entity.*;
import com.zyphora.order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    private String email() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    public Object placeOrder(Long addressId, String paymentMethod) {

        var cart = cartRepository.findByUserEmail(email());

        double total = 0;

        for (var item : cart) {
            total += item.getProduct().getPrice().doubleValue()
                    * item.getQuantity();
        }

        String paymentStatus = paymentMethod.equals("COD")
                ? paymentService.cod()
                : paymentService.online();

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

        return order;
    }

    public Object history() {
        return orderRepository.findByUserEmailOrderByIdDesc(email());
    }
}