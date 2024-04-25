package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;
import com.warehousesystem.app.search.strategy.BigDecimalPredicateStrategy;
import com.warehousesystem.app.search.strategy.IntegerPredicateStrategy;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

public class IntegerSearchCriteria implements SearchCriteria<Integer> {

    @Getter
    private PredicateStrategy<Integer> strategy = new IntegerPredicateStrategy();

    private String field;

    private String operation;

    @NotNull
    private Integer value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public Integer getValue() {
        return value;
    }

}
