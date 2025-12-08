package com.microtech.microtech.controller;

import com.microtech.microtech.dto.request.order.CreateOrderRequest;
import com.microtech.microtech.dto.response.order.OrderResponse;
import com.microtech.microtech.security.annotation.RolesAllowed;
import com.microtech.microtech.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @RolesAllowed(roles = {"ADMIN"})
    @PostMapping
    public ResponseEntity<String> create(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        return ResponseEntity.ok(orderService.create(request));
    }

    @RolesAllowed(roles = {"ADMIN"})
    @GetMapping("/client/{id}")
    public ResponseEntity<List<OrderResponse>> ordersOfClient(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(orderService.ordersOfClient(id));
    }
}
