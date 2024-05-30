package com.warehousesystem.app.kafka.event.impl;
import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.kafka.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderEventImpl implements Event {
    private com.warehousesystem.app.enums.Event event;
    private Long customerId;
    private OrderCreateDto order;
}