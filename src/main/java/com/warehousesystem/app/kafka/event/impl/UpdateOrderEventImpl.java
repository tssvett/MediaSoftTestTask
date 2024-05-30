package com.warehousesystem.app.kafka.event.impl;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.kafka.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderEventImpl implements Event {
    private com.warehousesystem.app.enums.Event event;
    private Long customerId;
    private UUID orderId;
    private OrderCreateDto order;
}
