package com.warehousesystem.app.dto.orchestrator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrchestratorRequestDto {
    private String login;
    private String account;
    private String inn;
    private String deliveryAddress;
    private BigDecimal totalPrice;
    private Long customerId;
    private String orderId;
}