package com.microtech.microtech.dto.response.payment;

import com.microtech.microtech.model.enums.PaymentStatus;
import lombok.Setter;

import java.time.LocalDate;

public record ChequeResponse(
        String reference,
        PaymentStatus status,
        String bank,
        LocalDate echeance
) implements PaymentMethodResponse {
}
