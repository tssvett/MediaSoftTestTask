package com.warehousesystem.app.search.criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.warehousesystem.app.search.enums.OperationType;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        include = JsonTypeInfo.As.PROPERTY,
        property = "field")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UUIDSearchCriteria.class, name = "id"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "name"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "article"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "description"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "category"),
        @JsonSubTypes.Type(value = BigDecimalSearchCriteria.class, name = "price"),
        @JsonSubTypes.Type(value = BigDecimalSearchCriteria.class, name = "quantity"),
        @JsonSubTypes.Type(value = LocalDateTimeSearchCriteria.class, name = "lastUpdateTime"),
        @JsonSubTypes.Type(value = LocalDateTimeSearchCriteria.class, name = "creationTime")
})

public interface SearchCriteria<T> {
    String getField();

    @NotNull
    T getValue();

    @NotNull
    OperationType getOperation();

    PredicateStrategy<T> getStrategy();
}
