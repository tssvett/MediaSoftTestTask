package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;
import com.warehousesystem.app.search.strategy.BigDecimalPredicateStrategy;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BigDecimalSearchCriteria implements SearchCriteria<BigDecimal> {

    private static PredicateStrategy<BigDecimal> STRATEGY = new BigDecimalPredicateStrategy();

    private String field;

    private OperationType operation;

    @NotNull
    private BigDecimal value;

    @Override
    public PredicateStrategy<BigDecimal> getStrategy() {
        return STRATEGY;
    }
}

