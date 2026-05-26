package com.zyphora.admin.controller;

import com.zyphora.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    /** GET /api/v1/admin/dashboard — counts + revenue */
    @GetMapping("/dashboard")
    public Object dashboard() {
        return service.dashboard();
    }

    /** GET /api/v1/admin/users — all registered users */
    @GetMapping("/users")
    public Object users() {
        return service.users();
    }

    /** GET /api/v1/admin/sellers — all seller applications */
    @GetMapping("/sellers")
    public Object sellers() {
        return service.sellers();
    }

    /** PUT /api/v1/admin/seller/{id}/approve — approve a seller */
    @PutMapping("/seller/{id}/approve")
    public Object approve(@PathVariable Long id) {
        return service.approveSeller(id);
    }

    /** PUT /api/v1/admin/seller/{id}/reject — reject a seller */
    @PutMapping("/seller/{id}/reject")
    public Object reject(@PathVariable Long id) {
        return service.rejectSeller(id);
    }

    /** GET /api/v1/admin/orders — all orders across platform */
    @GetMapping("/orders")
    public Object orders() {
        return service.allOrders();
    }

    /** PUT /api/v1/admin/order/{id}/status?status=SHIPPED — update order status */
    @PutMapping("/order/{id}/status")
    public Object updateOrderStatus(@PathVariable Long id,
                                    @RequestParam String status) {
        return service.updateOrderStatus(id, status);
    }
}
