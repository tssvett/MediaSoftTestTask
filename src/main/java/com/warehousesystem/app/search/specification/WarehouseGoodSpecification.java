package com.warehousesystem.app.search.specification;

import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.search.criteria.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WarehouseGoodSpecification {

    private final List<SearchCriteria> criteriaList;

    public Specification<WarehouseGood> createSpecification() {
        List<Specification<WarehouseGood>> specs = criteriaList.stream()
                .map(this::getSpecification)
                .collect(Collectors.toList());

        return Specification.allOf(specs);
    }

    private Specification<WarehouseGood> getSpecification(SearchCriteria criteria) {
        return (root, query, cb) ->
                switch (criteria.getOperation()) {
                    case EQUALS ->
                            criteria.getStrategy().getEqualsPattern(root.get(criteria.getField()), criteria.getValue(), cb);
                    case LESS_THAN_OR_EQ ->
                            criteria.getStrategy().getRightLimitPattern(root.get(criteria.getField()), criteria.getValue(), cb);
                    case GRATER_THAN_OR_EQ ->
                            criteria.getStrategy().getLeftLimitPattern(root.get(criteria.getField()), criteria.getValue(), cb);
                    case LIKE ->
                            criteria.getStrategy().getLikePattern(root.get(criteria.getField()), criteria.getValue(), cb);
                };
    }
}
