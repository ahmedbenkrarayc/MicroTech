package com.microtech.microtech.mapper;

import com.microtech.microtech.dto.response.order.OrderResponse;
import com.microtech.microtech.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "total", target = "TTC")
    OrderResponse toResponse(Order order);
}
