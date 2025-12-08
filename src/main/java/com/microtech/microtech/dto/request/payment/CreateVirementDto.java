package com.microtech.microtech.dto.request.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateVirementDto(
        @NotBlank(message = "Reference cannot be empty")
        @Size(min = 3, max = 50, message = "Reference must be between 3 and 50 characters")
        String reference,

        @NotBlank(message = "Bank cannot be empty")
        @Size(min = 3, max = 50, message = "Bank must be between 3 and 50 characters")
        String bank
) implements PaymentMethod {
}
