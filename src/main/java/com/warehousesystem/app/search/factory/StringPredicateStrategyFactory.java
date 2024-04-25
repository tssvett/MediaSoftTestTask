package com.warehousesystem.app.search.factory;

import com.warehousesystem.app.search.strategy.PredicateStrategy;
import com.warehousesystem.app.search.strategy.StringPredicateStrategy;

public class StringPredicateStrategyFactory extends PredicateStrategyFactory<String> {

    @Override
    public PredicateStrategy<String> createPredicateStrategy(String value) {
        return new StringPredicateStrategy();
    }
}