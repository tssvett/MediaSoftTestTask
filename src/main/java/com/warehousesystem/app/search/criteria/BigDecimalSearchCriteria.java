package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;

import com.warehousesystem.app.search.strategy.BigDecimalPredicateStrategy;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import com.warehousesystem.app.search.strategy.StringPredicateStrategy;
import com.warehousesystem.app.search.strategy.UUIDPredicateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

public class BigDecimalSearchCriteria implements SearchCriteria<BigDecimal> {

    @Getter
    private PredicateStrategy<BigDecimal> strategy = new BigDecimalPredicateStrategy();

    private String field;

    private String operation;

    @NotNull
    private BigDecimal value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

}

