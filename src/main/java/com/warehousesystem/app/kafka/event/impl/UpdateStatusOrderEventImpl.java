package com.warehousesystem.app.kafka.event.impl;

import com.warehousesystem.app.dto.status.StatusResponseDto;
import com.warehousesystem.app.enums.Status;
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
public class UpdateStatusOrderEventImpl implements Event {
    private com.warehousesystem.app.enums.Event event;
    private UUID orderId;
    private Long customerId;
    private StatusResponseDto status;
}
