package com.warehousesystem.app.search.criteria;

import com.warehousesystem.app.search.enums.OperationType;

import jakarta.validation.constraints.NotBlank;

import java.util.Locale;

public class StringSearchCriteria implements SearchCriteria<String> {

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
