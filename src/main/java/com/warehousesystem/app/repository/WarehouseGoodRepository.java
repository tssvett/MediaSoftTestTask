package com.warehousesystem.app.repository;

import com.warehousesystem.app.model.WarehouseGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface WarehouseGoodRepository extends JpaRepository<WarehouseGood, UUID> {
    
    WarehouseGood getReferenceById(UUID id);
    
    WarehouseGood getReferenceByArticle(String name);

    boolean existsByArticle(String name);

    void deleteByArticle(String name);
}