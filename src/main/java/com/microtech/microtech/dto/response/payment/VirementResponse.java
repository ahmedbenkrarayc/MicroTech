package com.microtech.microtech.dto.response.payment;

import com.microtech.microtech.model.enums.PaymentStatus;

public record VirementResponse(
        String reference,
        PaymentStatus status,
        String bank
) implements PaymentMethodResponse {
}
