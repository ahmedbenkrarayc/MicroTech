package com.microtech.microtech.event;

import com.microtech.microtech.dto.request.order.OrderProductDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateStockEvent {
    private final OrderProductDto orderProduct;
}
