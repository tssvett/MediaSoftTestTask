package com.warehousesystem.app.repository;

import com.warehousesystem.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
            select o from Order o
            left join fetch o.preparedProducts pp
            left join fetch pp.product p
            where o.id = :orderId
            """)
    Optional<Order> findByIdPreparedProducts(UUID orderId);
}
