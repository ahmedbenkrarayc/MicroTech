package com.microtech.microtech.model;

import com.microtech.microtech.model.enums.CustomerTier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    @NotBlank(message = "CIN is required")
    @Size(min = 4, max = 20, message = "CIN must be between 4 and 20 characters")
    @Column(nullable = false, unique = true)
    private String cin;

    @Enumerated(EnumType.STRING)
    private CustomerTier tier;
}
