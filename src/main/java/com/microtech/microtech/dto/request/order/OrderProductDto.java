package com.microtech.microtech.dto.request.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderProductDto(
        @NotNull(message = "Product ID is required")
        Long id,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {
}
