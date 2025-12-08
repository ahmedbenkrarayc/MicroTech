package com.microtech.microtech.dto.request.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaymentRequest(
        @Positive(message = "Price must be greater than 0")
        float price,

        @NotNull(message = "Order ID cannot be null")
        Long orderId,

        @NotNull(message = "Payment method cannot be null")
        @Valid
        PaymentMethod method
) {
}
