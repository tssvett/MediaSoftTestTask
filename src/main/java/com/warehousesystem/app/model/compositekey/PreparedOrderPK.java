package com.warehousesystem.app.model.compositekey;

import com.warehousesystem.app.model.Customer;
import com.warehousesystem.app.model.Order;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class PreparedOrderPK implements Serializable {



    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customerId;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    private Order orderId;

}
