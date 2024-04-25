package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;

import com.warehousesystem.app.search.strategy.PredicateStrategy;
import com.warehousesystem.app.search.strategy.StringPredicateStrategy;
import com.warehousesystem.app.search.strategy.UUIDPredicateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Locale;
import java.util.UUID;

public class UUIDSearchCriteria implements SearchCriteria<UUID> {

    @Getter
    private PredicateStrategy<UUID> strategy = new UUIDPredicateStrategy();

    private String field;

    private String operation;

    @NotNull
    private UUID value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public UUID getValue() {
        return value;
    }

}

