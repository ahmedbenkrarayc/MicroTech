package com.microtech.microtech.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@DiscriminatorValue("virement")
public class Virement extends PaymentMethod {
    @NotBlank(message = "Bank name cannot be blank")
    private String bank;
}
