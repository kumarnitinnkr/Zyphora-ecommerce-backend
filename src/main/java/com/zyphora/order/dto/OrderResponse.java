package com.zyphora.order.dto;

import com.zyphora.order.entity.OrderEntity;
import com.zyphora.order.entity.OrderItem;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/// ─── OrderResponse ────────────────────────────────────────────────────────────
/// Full order DTO returned to the Flutter app.
/// Includes: order info + address + items (with product details).
@Getter
public class OrderResponse {

    private final Long          id;
    private final BigDecimal    totalAmount;
    private final String        paymentMethod;
    private final String        paymentStatus;
    private final String        status;
    private final LocalDateTime createdAt;

    // Flattened address (so Flutter doesn't hit lazy-load issues)
    private final AddressDto address;

    // Order line items
    private final List<ItemDto> items;

    public OrderResponse(OrderEntity order, List<OrderItem> orderItems) {
        this.id            = order.getId();
        this.totalAmount   = order.getTotalAmount();
        this.paymentMethod = order.getPaymentMethod();
        this.paymentStatus = order.getPaymentStatus();
        this.status        = order.getStatus() != null ? order.getStatus().name() : "PLACED";
        this.createdAt     = order.getCreatedAt();

        // Map address
        var addr = order.getAddress();
        this.address = addr != null ? new AddressDto(
                addr.getId(),
                addr.getFullName(),
                addr.getMobile(),
                addr.getLine1(),
                addr.getLine2(),
                addr.getCity(),
                addr.getState(),
                addr.getPincode(),
                addr.getCountry()
        ) : null;

        // Map items
        this.items = orderItems.stream()
                .map(item -> new ItemDto(
                        item.getId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getProduct() != null ? new ProductDto(
                                item.getProduct().getId(),
                                item.getProduct().getTitle(),
                                item.getProduct().getImageUrl(),
                                item.getProduct().getPrice()
                        ) : null
                ))
                .collect(Collectors.toList());
    }

    // ── Inner DTOs ─────────────────────────────────────────────────────────────

    @Getter
    public static class AddressDto {
        private final Long   id;
        private final String fullName;
        private final String mobile;
        private final String line1;
        private final String line2;
        private final String city;
        private final String state;
        private final String pincode;
        private final String country;

        public AddressDto(Long id, String fullName, String mobile, String line1,
                          String line2, String city, String state,
                          String pincode, String country) {
            this.id       = id;
            this.fullName = fullName;
            this.mobile   = mobile;
            this.line1    = line1;
            this.line2    = line2;
            this.city     = city;
            this.state    = state;
            this.pincode  = pincode;
            this.country  = country;
        }
    }

    @Getter
    public static class ItemDto {
        private final Long       id;
        private final Integer    quantity;
        private final BigDecimal price;
        private final ProductDto product;

        public ItemDto(Long id, Integer quantity, BigDecimal price, ProductDto product) {
            this.id       = id;
            this.quantity = quantity;
            this.price    = price;
            this.product  = product;
        }
    }

    @Getter
    public static class ProductDto {
        private final Long       id;
        private final String     title;
        private final String     imageUrl;
        private final BigDecimal price;

        public ProductDto(Long id, String title, String imageUrl, BigDecimal price) {
            this.id       = id;
            this.title    = title;
            this.imageUrl = imageUrl;
            this.price    = price;
        }
    }
}
