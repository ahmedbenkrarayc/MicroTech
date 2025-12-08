package com.microtech.microtech.repository;

import com.microtech.microtech.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.client.id = :clientId")
    Double getTotalOrdersByClientId(@Param("clientId") Long clientId);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.client.id = :clientId AND o.status = com.microtech.microtech.model.enums.OrderStatus.CONFIRMED")
    Double getTotalConfirmedOrdersByClientId(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.client.id = :clientId AND o.status = com.microtech.microtech.model.enums.OrderStatus.CONFIRMED")
    Long countConfirmedOrdersByClientId(@Param("clientId") Long clientId);
}
