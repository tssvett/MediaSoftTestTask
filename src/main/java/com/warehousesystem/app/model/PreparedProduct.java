package com.warehousesystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.warehousesystem.app.model.compositekey.PreparedOrderPK;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "prepared_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class PreparedProduct {

    @EmbeddedId
    private PreparedOrderPK pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private BigDecimal quantity;

}
