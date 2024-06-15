package com.warehousesystem.app.repository;

import com.warehousesystem.app.model.PreparedProduct;
import com.warehousesystem.app.model.compositekey.PreparedOrderPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PreparedProductRepository extends JpaRepository<PreparedProduct, PreparedOrderPK> {

    List<PreparedProduct> findAllByOrderId(@Param("order_id") UUID orderId);
}
