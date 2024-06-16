package com.warehousesystem.app.repository;

import com.warehousesystem.app.enums.Status;
import com.warehousesystem.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.status = 'CREATED' or o.status = 'CONFIRMED'")
    List<Order> findAllByStatus();
}
