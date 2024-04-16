package com.warehousesystem.app.utils;

import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodSearchDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.model.WarehouseGood;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public WarehouseGoodFullDto mapToWarehouseGoodFullDto(WarehouseGood warehouseGood) {

        return WarehouseGoodFullDto.builder()
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

    public WarehouseGood mapFullToWarehouseGood(WarehouseGoodFullDto warehouseGoodFullDto) {

        return WarehouseGood.builder()
                .id(warehouseGoodFullDto.getId())
                .name(warehouseGoodFullDto.getName())
                .article(warehouseGoodFullDto.getArticle())
                .description(warehouseGoodFullDto.getDescription())
                .category(warehouseGoodFullDto.getCategory())
                .price(warehouseGoodFullDto.getPrice())
                .quantity(warehouseGoodFullDto.getQuantity())
                .build();
    }

    public WarehouseGood mapUpdateToWarehouseGood(WarehouseGoodUpdateDto warehouseGoodUpdateDto) {

        return WarehouseGood.builder()
                .name(warehouseGoodUpdateDto.getName())
                .article(warehouseGoodUpdateDto.getArticle())
                .description(warehouseGoodUpdateDto.getDescription())
                .category(warehouseGoodUpdateDto.getCategory())
                .price(warehouseGoodUpdateDto.getPrice())
                .quantity(warehouseGoodUpdateDto.getQuantity())
                .build();
    }

    public WarehouseGoodUpdateDto mapToWarehouseGoodUpdateDto(WarehouseGood warehouseGood) {

        return WarehouseGoodUpdateDto.builder()
                .name(warehouseGood.getName())
                .article(warehouseGood.getArticle())
                .description(warehouseGood.getDescription())
                .category(warehouseGood.getCategory())
                .price(warehouseGood.getPrice())
                .quantity(warehouseGood.getQuantity())
                .build();
    }


}
