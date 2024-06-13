package com.warehousesystem.app.scheduling.impl;

import com.warehousesystem.app.annotation.MeasureWorkingTime;
import com.warehousesystem.app.handler.exception.EmptyProductException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;
import com.warehousesystem.app.model.Product;
import com.warehousesystem.app.repository.ProductRepository;
import com.warehousesystem.app.scheduling.SimpleGoodsPriceScheduler;
import com.warehousesystem.app.scheduling.conditions.SchedulingAndNotOptimizedCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Conditional(SchedulingAndNotOptimizedCondition.class)
@EnableScheduling
public class SimpleGoodsPriceSchedulerImpl implements SimpleGoodsPriceScheduler {

    @Value("${app.scheduling.priceIncreasePercentage}")
    private int percentage;
    private final ProductRepository productRepository;

    public SimpleGoodsPriceSchedulerImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Transactional
    @MeasureWorkingTime
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    public void changeGoodsValue() throws NotFoundByIdException, SQLUniqueException, EmptyProductException {
        List<Product> goods = productRepository.findAll()
                .stream()
                .map(this::increasePrice).toList();
        productRepository.saveAll(goods);
    }

    private Product increasePrice(Product good) {
        good.setPrice(good.getPrice().multiply(BigDecimal.valueOf(100 + percentage).divide(BigDecimal.valueOf(100))));
        return good;
    }
}
