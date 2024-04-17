package com.warehousesystem.app.scheduling.impl;

import com.warehousesystem.app.annotation.MeasureWorkingTime;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.scheduling.SimpleGoodsPriceScheduler;
import com.warehousesystem.app.scheduling.conditions.SchedulingAndNotOptimizedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Conditional(SchedulingAndNotOptimizedCondition.class)
@EnableScheduling
public class SimpleGoodsPriceSchedulerImpl implements SimpleGoodsPriceScheduler {

    @Value("${app.scheduling.priceIncreasePercentage}")
    private int percentage;
    @Autowired
    private WarehouseGoodRepository warehouseGoodRepository;


    @Transactional
    @MeasureWorkingTime
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    public void changeGoodsValue() throws NotFoundByIdException, SQLUniqueException, EmptyGoodsException {
        List<WarehouseGood> goods = warehouseGoodRepository.findAll()
                        .stream()
                        .map(this::increasePrice).toList();
        warehouseGoodRepository.saveAll(goods);
    }

    private WarehouseGood increasePrice(WarehouseGood good){
        good.setPrice(good.getPrice() * (100 + percentage) / 100);
        return good;
    }
}
