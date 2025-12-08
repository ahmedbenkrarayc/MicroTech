package com.microtech.microtech.dto.response.order;

import com.microtech.microtech.model.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        LocalDateTime date,
        Double TTC,
        OrderStatus status
) {
}
