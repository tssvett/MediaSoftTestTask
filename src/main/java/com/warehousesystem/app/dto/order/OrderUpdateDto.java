package com.warehousesystem.app.dto.order;

import com.warehousesystem.app.dto.product.ProductOrderDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateDto {

    @NotBlank(message = "Delivery address cannot be empty")
    private String deliveryAddress;


    @NotNull(message = "Products to order cannot be empty")
    @Valid
    private List<ProductOrderDto> products;

}
