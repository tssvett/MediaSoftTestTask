package com.warehousesystem.app.dto.product;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchDto {

    @Positive
    @Builder.Default
    private Integer size = 100;

    @PositiveOrZero
    @Builder.Default
    private Integer pageNumber = 0;
}
