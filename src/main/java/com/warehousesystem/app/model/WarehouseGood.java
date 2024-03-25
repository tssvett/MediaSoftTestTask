package com.warehousesystem.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.warehousesystem.app.converter.DateConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "warehouse_goods")
@Getter
@Setter
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class WarehouseGood {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(name = "article", unique = true)
    @NotBlank(message = "Article cannot be blank")
    private String article;

    @Column(name = "description")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Category cannot be blank")
    @Column(name = "category")
    private String category;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price cannot be negative or zero")
    @Column(name = "price")
    private Double price;

    @NotNull
    @Min(value = 1, message = "Quantity cannot be negative or zero")
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "last_update")
    @UpdateTimestamp
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateTime;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Convert(converter = DateConverter.class)
    private LocalDateTime creationTime;
}