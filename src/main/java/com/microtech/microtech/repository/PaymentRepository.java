package com.microtech.microtech.repository;

import com.microtech.microtech.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findFirstByOrderIdOrderByPaymentDateDesc(Long orderId);

    @Query("SELECT COALESCE(SUM(p.price), 0) FROM Payment p WHERE p.order.id = :orderId")
    double sumPricesByOrderId(@Param("orderId") Long orderId);
}
