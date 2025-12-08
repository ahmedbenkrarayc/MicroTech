package com.microtech.microtech.dto.response.payment;

import com.microtech.microtech.model.enums.PaymentStatus;

public record EspeceResponse(
        String reference,
        PaymentStatus status
) implements PaymentMethodResponse {
}
