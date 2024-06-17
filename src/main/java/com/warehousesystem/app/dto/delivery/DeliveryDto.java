package com.warehousesystem.app.dto.delivery;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    @NotNull(message = "Status can not be null")
    private LocalDateTime deliveryDate;
}
