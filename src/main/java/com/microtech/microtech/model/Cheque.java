package com.microtech.microtech.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@DiscriminatorValue("cheque")
public class Cheque extends PaymentMethod {
    @NotBlank(message = "Bank name cannot be blank")
    private String bank;

    @FutureOrPresent(message = "Echeance date must be today or in the future")
    private LocalDate echeance;
}
