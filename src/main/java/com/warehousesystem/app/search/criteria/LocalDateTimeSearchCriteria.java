package com.warehousesystem.app.search.criteria;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.warehousesystem.app.search.enums.OperationType;
import com.warehousesystem.app.search.strategy.LocalDateTimePredicateStrategy;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LocalDateTimeSearchCriteria implements SearchCriteria<LocalDateTime> {

    private static PredicateStrategy<LocalDateTime> STRATEGY = new LocalDateTimePredicateStrategy();

    private String field;

    private OperationType operation;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime value;

    @Override
    public PredicateStrategy<LocalDateTime> getStrategy() {
        return STRATEGY;
    }
}


