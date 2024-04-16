package com.warehousesystem.app.scheduling.impl;

import com.warehousesystem.app.annotation.NonTransactionalExecutionTime;
import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodSearchDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.scheduling.SimpleGoodsPriceScheduler;
import com.warehousesystem.app.scheduling.conditions.SchedulingAndNotOptimizedCondition;
import com.warehousesystem.app.service.WarehouseGoodService;
import com.warehousesystem.app.utils.MappingUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Conditional(SchedulingAndNotOptimizedCondition.class)
public class SimpleGoodsPriceSchedulerImpl implements SimpleGoodsPriceScheduler {

    @Value("${app.scheduling.priceIncreasePercentage}")
    private int percentage;
    @Autowired
    private WarehouseGoodRepository warehouseGoodRepository;
    @Autowired
    private MappingUtils mappingUtils;

    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @NonTransactionalExecutionTime
    @Transactional
    //Увеличивает цену всех товаров на 10%
    public void changeGoodsValue() throws NotFoundByIdException, SQLUniqueException, EmptyGoodsException {
        System.out.println("Simple scheduler START working!");
        Stream<WarehouseGood> goodsStream = warehouseGoodRepository.findAll().stream();
        goodsStream.forEach(good -> {
            good.setPrice(good.getPrice() * (100 + percentage) / 100);
            warehouseGoodRepository.save(good);
            System.out.println(good.getPrice());
        });
        System.out.println("Simple scheduler END working!");

    }
}
