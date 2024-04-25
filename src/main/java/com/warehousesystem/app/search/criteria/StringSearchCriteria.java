package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;

import com.warehousesystem.app.search.strategy.PredicateStrategy;
import com.warehousesystem.app.search.strategy.StringPredicateStrategy;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Locale;

public class StringSearchCriteria implements SearchCriteria<String> {

    @Getter
    private PredicateStrategy<String> strategy = new StringPredicateStrategy();

    private String field;

    private String operation;

    @NotBlank
    private String value;

   @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getValue() {
        return value;
    }

}
