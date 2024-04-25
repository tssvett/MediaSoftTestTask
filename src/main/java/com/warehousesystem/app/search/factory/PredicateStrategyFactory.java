package com.warehousesystem.app.search.factory;

import com.warehousesystem.app.search.strategy.PredicateStrategy;

public abstract class PredicateStrategyFactory<T> {

    public abstract PredicateStrategy<T> createPredicateStrategy(T value);

}