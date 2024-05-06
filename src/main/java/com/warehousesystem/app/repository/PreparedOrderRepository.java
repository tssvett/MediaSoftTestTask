package com.warehousesystem.app.repository;

import com.warehousesystem.app.model.PreparedOrder;
import com.warehousesystem.app.model.compositekey.PreparedOrderPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreparedOrderRepository extends JpaRepository<PreparedOrder, PreparedOrderPK> {
}
