package com.warehousesystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.warehousesystem.app.converter.DateConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Product {
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

    @Column(name = "is_available")
    private boolean isAvailable = true;

    @Column(name = "last_update")
    @UpdateTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateTime;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "product")
    private List<PreparedProduct> preparedProducts;
}