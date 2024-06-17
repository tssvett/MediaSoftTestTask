package com.warehousesystem.app.dto.order;

import com.warehousesystem.app.dto.customer.CustomerInfo;
import com.warehousesystem.app.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfo {

    UUID id;

    CustomerInfo customer;

    Status status;

    String deliveryAddress;

    BigDecimal quantity;
}
