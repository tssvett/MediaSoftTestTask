package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import com.warehousesystem.app.search.strategy.StringPredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StringSearchCriteria implements SearchCriteria<String> {

    private static PredicateStrategy<String> STRATEGY = new StringPredicateStrategy();

    private String field;

    private OperationType operation;

    @NotNull
    private String value;

    @Override
    public PredicateStrategy<String> getStrategy() {
        return STRATEGY;
    }
}
