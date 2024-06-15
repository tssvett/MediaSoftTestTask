package com.warehousesystem.app.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDto {

    @NotNull(message = "Id cannot be empty")
    private UUID id;

    @Positive(message = "Quantity must me positive number")
    @NotNull(message = "Quantity cannot be empty")
    private BigDecimal quantity;
}
