package com.warehousesystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.warehousesystem.app.converter.DateConverter;
import com.warehousesystem.app.enums.CurrencyType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "warehouse_goods")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class WarehouseGood {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "article", unique = true)
    private String article;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private CurrencyType currency;

    @Column(name = "last_update")
    @UpdateTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateTime;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime creationTime;
}