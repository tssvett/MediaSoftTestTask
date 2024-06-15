package com.warehousesystem.app.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetResponseDto {

    @NotNull(message = "Id cannot be empty")
    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Positive(message = "Quantity must me positive number")
    @NotNull(message = "Quantity cannot be empty")
    private BigDecimal quantity;

    @Positive(message = "Price must me positive number")
    @NotNull(message = "Price cannot be empty")
    private BigDecimal price;
}
