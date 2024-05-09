package com.warehousesystem.app.model.compositekey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreparedOrderPK implements Serializable {

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "order_id")
    private UUID orderId;

}
