package com.warehousesystem.app.utils;

import com.warehousesystem.app.dto.product.*;
import com.warehousesystem.app.model.PreparedProduct;
import com.warehousesystem.app.model.Product;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public ProductFullDto mapToWarehouseGoodFullDto(Product warehouseGood) {

        return ProductFullDto.builder()
                .id(warehouseGood.getId())
                .name(warehouseGood.getName())
                .article(warehouseGood.getArticle())
                .description(warehouseGood.getDescription())
                .category(warehouseGood.getCategory())
                .price(warehouseGood.getPrice())
                .quantity(warehouseGood.getQuantity())
                .lastUpdateTime(warehouseGood.getLastUpdateTime())
                .creationTime(warehouseGood.getCreationTime())
                .build();
    }

    public Product mapFullToWarehouseGood(ProductFullDto warehouseGoodFullDto) {

        return Product.builder()
                .id(warehouseGoodFullDto.getId())
                .name(warehouseGoodFullDto.getName())
                .article(warehouseGoodFullDto.getArticle())
                .description(warehouseGoodFullDto.getDescription())
                .category(warehouseGoodFullDto.getCategory())
                .price(warehouseGoodFullDto.getPrice())
                .quantity(warehouseGoodFullDto.getQuantity())
                .build();
    }

    public Product mapUpdateToWarehouseGood(ProductUpdateDto warehouseGoodUpdateDto) {

        return Product.builder()
                .name(warehouseGoodUpdateDto.getName())
                .article(warehouseGoodUpdateDto.getArticle())
                .description(warehouseGoodUpdateDto.getDescription())
                .category(warehouseGoodUpdateDto.getCategory())
                .price(warehouseGoodUpdateDto.getPrice())
                .quantity(warehouseGoodUpdateDto.getQuantity())
                .build();
    }

    public Product mapUpdateToWarehouseGood(ProductCreateDto warehouseGoodCreateDto) {

        return Product.builder()
                .name(warehouseGoodCreateDto.getName())
                .article(warehouseGoodCreateDto.getArticle())
                .description(warehouseGoodCreateDto.getDescription())
                .category(warehouseGoodCreateDto.getCategory())
                .price(warehouseGoodCreateDto.getPrice())
                .quantity(warehouseGoodCreateDto.getQuantity())
                .build();
    }

    public ProductUpdateDto mapToWarehouseGoodUpdateDto(Product warehouseGood) {

        return ProductUpdateDto.builder()
                .name(warehouseGood.getName())
                .article(warehouseGood.getArticle())
                .description(warehouseGood.getDescription())
                .category(warehouseGood.getCategory())
                .price(warehouseGood.getPrice())
                .quantity(warehouseGood.getQuantity())
                .build();
    }

    public ProductOrderDto mapPreparedProductToProductOrderDto(PreparedProduct preparedProduct){

        return ProductOrderDto.builder()
                .id(preparedProduct.getPk().getProductId())
                .quantity(preparedProduct.getQuantity()).build();

    }

    public ProductGetResponseDto mapPreparedProductToProductGetResponseDto(PreparedProduct preparedProduct){

        return ProductGetResponseDto.builder()
                .id(preparedProduct.getPk().getProductId())
                .name(preparedProduct.getProduct().getName())
                .quantity(preparedProduct.getQuantity())
                .price(preparedProduct.getPrice()).build();
    }


}
