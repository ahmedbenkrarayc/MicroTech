package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.order.CreateOrderRequest;
import com.microtech.microtech.dto.response.order.OrderResponse;

import java.util.List;

public interface OrderService {
    String create(CreateOrderRequest request);
    List<OrderResponse> ordersOfClient(Long clientId);
}
