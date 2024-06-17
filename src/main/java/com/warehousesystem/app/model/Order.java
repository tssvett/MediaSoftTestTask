package com.warehousesystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.warehousesystem.app.converter.DateConverter;
import com.warehousesystem.app.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "delivery_time")
    @Convert(converter = DateConverter.class)
    private LocalDateTime deliveryTime;

    @Column(name = "business_key")
    private UUID businessKey;

    @OneToMany(mappedBy = "order")
    private List<PreparedProduct> preparedProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customer;


}