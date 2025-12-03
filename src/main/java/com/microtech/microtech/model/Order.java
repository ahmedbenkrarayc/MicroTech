package com.microtech.microtech.model;

import com.microtech.microtech.model.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", inclusive = true, message = "Subtotal must be >= 0")
    @Column(nullable = false)
    private float subtotal;

    @DecimalMin(value = "0.0", inclusive = true, message = "Remise must be >= 0")
    @Column(nullable = false)
    private float remise;

    @DecimalMin(value = "0.0", inclusive = true, message = "TVA must be >= 0")
    @Column(nullable = false)
    private float tva;

    @DecimalMin(value = "0.0", inclusive = true, message = "Total must be >= 0")
    @Column(nullable = false)
    private float total;

    @Size(max = 50, message = "Promocode cannot exceed 50 characters")
    private String promocode;

    @NotNull(message = "Order date is required")
    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull(message = "Order status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItem;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Payment> payments;
}