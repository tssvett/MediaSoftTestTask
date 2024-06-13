package com.warehousesystem.app.repository;

import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.search.criteria.SearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;


@Repository
public interface WarehouseGoodRepository extends JpaRepository<WarehouseGood, UUID>, JpaSpecificationExecutor<WarehouseGood> {
    
    WarehouseGood getReferenceById(UUID id);
    
    WarehouseGood getReferenceByArticle(String name);

    boolean existsByArticle(String name);

    void deleteByArticle(String name);

    @Query("select w from WarehouseGood w order by w.price asc")
    List<WarehouseGood> findAll(Pageable pageable);

}