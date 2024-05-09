package com.warehousesystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.warehousesystem.app.enums.Status;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @OneToMany(mappedBy = "order")
    private Set<PreparedProduct> preparedProducts;


}