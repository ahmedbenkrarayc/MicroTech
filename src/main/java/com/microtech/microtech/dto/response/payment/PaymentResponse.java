package com.microtech.microtech.dto.response.payment;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PaymentResponse(
        Long id,
        Long orderId,
        int paymentNumber,
        float price,
        LocalDate paymentDate,
        LocalDate dateEncaissement,
        PaymentMethodResponse method
) {
}
