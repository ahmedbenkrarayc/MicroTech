package com.microtech.microtech.dto.request.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record CreateOrderRequest(
        @Pattern(
                regexp = "^PROMO-[A-Z0-9]{4}$",
                message = "Promo code must follow the format PROMO-XXXX (uppercase letters and digits)"
        )
        String promocode,

        @NotNull(message = "Client ID is required")
        Long clientId,

        @NotEmpty(message = "Order must contain at least one product")
        @Valid
        List<OrderProductDto> products
) {
}
