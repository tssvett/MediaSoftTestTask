package com.warehousesystem.app.dto.order;

import com.warehousesystem.app.dto.product.ProductGetResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetResponseDto {

    @NotNull(message = "Id cannot be empty")
    private UUID id;

    @NotNull(message = "Products cannot be empty")
    @Valid
    private List<ProductGetResponseDto> products;

    @NotNull(message = "Total price cannot be empty")
    @Positive(message = "Total price must be positive")
    private BigDecimal totalPrice;
}
