package com.microtech.microtech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Payment number must be at least 1")
    @Column(nullable = false, unique = true)
    private int paymentNumber;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false)
    private float price;

    @NotNull(message = "Payment date is required")
    @Column(nullable = false)
    private LocalDate paymentDate;

    @NotNull(message = "Date of encaissement is required")
    @Column(nullable = false)
    private LocalDate dateEncaissement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private PaymentMethod method;
}
