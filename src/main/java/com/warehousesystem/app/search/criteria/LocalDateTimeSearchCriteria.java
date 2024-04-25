package com.warehousesystem.app.search.criteria;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.warehousesystem.app.search.enums.OperationType;

import com.warehousesystem.app.search.strategy.LocalDateTimePredicateStrategy;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeSearchCriteria implements SearchCriteria<LocalDateTime> {

    @Getter
    private PredicateStrategy<LocalDateTime> strategy = new LocalDateTimePredicateStrategy();

    private String field;

    private String operation;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }

}


