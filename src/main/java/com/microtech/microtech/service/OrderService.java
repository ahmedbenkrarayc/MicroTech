package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.order.CreateOrderRequest;

public interface OrderService {
    String create(CreateOrderRequest request);
}
