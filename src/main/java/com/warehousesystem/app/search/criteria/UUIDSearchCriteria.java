package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import com.warehousesystem.app.search.strategy.UUIDPredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UUIDSearchCriteria implements SearchCriteria<UUID> {

    private static PredicateStrategy<UUID> STRATEGY = new UUIDPredicateStrategy();

    private String field;

    private OperationType operation;

    @NotNull
    private UUID value;

    @Override
    public PredicateStrategy<UUID> getStrategy() {
        return STRATEGY;
    }
}

