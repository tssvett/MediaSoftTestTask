package com.warehousesystem.app.scheduling.impl;

import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.scheduling.OptimizedGoodsPriceScheduler;
import com.warehousesystem.app.scheduling.conditions.SchedulingAndOptimizationCondition;
import com.warehousesystem.app.service.WarehouseGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Conditional(SchedulingAndOptimizationCondition.class)
public class OptimizedGoodsPriceSchedulerImpl implements OptimizedGoodsPriceScheduler {

    @Value("${app.scheduling.priceIncreasePercentage}")
    private int percentage;
    @Autowired
    private WarehouseGoodService warehouseGoodService;

    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    //Увеличивает цену всех товаров на 10%
    public void changeGoodsValue() throws EmptyGoodsException, SQLUniqueException {
        System.out.println("Optimized scheduler");
        System.out.println(percentage);
        WarehouseGoodUpdateDto warehouseGoodUpdateDto = WarehouseGoodUpdateDto.builder()
                .price(100d)
                .article("article")
                .quantity(1)
                .name("good")
                .category("category")
                .description("description")
                .build();
        warehouseGoodService.create(warehouseGoodUpdateDto);
        System.out.println(warehouseGoodService.readAll().stream().map(good -> good.getPrice() * (100 + percentage) / 100).toArray()[0]);
    }
}
