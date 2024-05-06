package com.warehousesystem.app.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @Min(value = 1, message = "Quantity cannot be less than 1")
    @NotNull(message = "Quantity cannot be empty")
    private BigDecimal quantity;

}
