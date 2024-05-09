package com.warehousesystem.app.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Article cannot be blank")
    private String article;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price cannot be negative or zero")
    private BigDecimal price;

    @NotNull
    @Min(value = 1, message = "Quantity cannot be negative or zero")
    private BigDecimal quantity;

    @NotNull
    private boolean isAvailable = true;
}
