package com.microtech.microtech.repository;

import com.microtech.microtech.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.client.id = :clientId")
    Double getTotalOrdersByClientId(@Param("clientId") Long clientId);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.client.id = :clientId AND o.status = com.microtech.microtech.model.enums.OrderStatus.CONFIRMED")
    Double getTotalConfirmedOrdersByClientId(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.client.id = :clientId AND o.status = com.microtech.microtech.model.enums.OrderStatus.CONFIRMED")
    Long countConfirmedOrdersByClientId(@Param("clientId") Long clientId);

    List<Order> findByClientId(Long clientId);

    @Query("SELECT MIN(o.date) FROM Order o WHERE o.client.id = :clientId")
    Optional<LocalDateTime> getFirstOrderDate(@Param("clientId") Long clientId);

    @Query("SELECT MAX(o.date) FROM Order o WHERE o.client.id = :clientId")
    Optional<LocalDateTime> getLastOrderDate(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.client.id = :clientId")
    Long countOrdersByClientId(@Param("clientId") Long clientId);
}
