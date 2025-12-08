package com.microtech.microtech.dto.request.payment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateChequeDto(
        @NotBlank(message = "Reference cannot be empty")
        @Size(min = 3, max = 50, message = "Reference must be between 3 and 50 characters")
        String reference,

        @NotBlank(message = "Bank cannot be empty")
        @Size(min = 3, max = 50, message = "Bank must be between 3 and 50 characters")
        String bank,

        @NotNull(message = "Echeance date cannot be null")
        @Future(message = "Echeance date must be in the future")
        LocalDate echeance
) implements PaymentMethod {
}
