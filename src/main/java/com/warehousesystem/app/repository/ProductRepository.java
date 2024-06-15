package com.warehousesystem.app.repository;

import com.warehousesystem.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    Product getReferenceById(UUID id);

    Product getReferenceByArticle(String name);

    boolean existsByArticle(String name);

    void deleteByArticle(String name);

    @Query("select w from Product w order by w.price asc")
    List<Product> findAll(Pageable pageable);
}