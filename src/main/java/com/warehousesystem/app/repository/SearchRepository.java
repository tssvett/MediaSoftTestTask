package com.warehousesystem.app.repository;

import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.search.criteria.SearchCriteria;

import com.warehousesystem.app.search.enums.OperationType;
import com.warehousesystem.app.search.factory.PredicateStrategyFactory;
import com.warehousesystem.app.search.strategy.BigDecimalPredicateStrategy;
import com.warehousesystem.app.search.strategy.LocalDatePredicateStrategy;
import com.warehousesystem.app.search.strategy.PredicateStrategy;
import com.warehousesystem.app.search.strategy.StringPredicateStrategy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SearchRepository {
    EntityManager entityManager;

    public SearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

   public List<WarehouseGood> findAll(List<SearchCriteria<?>> searchCriteria, Pageable pageRequest) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WarehouseGood> cq = cb.createQuery(WarehouseGood.class);
        Root<WarehouseGood> root = cq.from(WarehouseGood.class);
        Predicate predicate = cb.conjunction();
        for (SearchCriteria<?> criteria:searchCriteria) {
            PredicateStrategy predicateStrategy = getPredicateStrategy(criteria);
            switch(criteria.getOperation()) {
                    case EQUALS -> predicate = cb.and(predicate, predicateStrategy.getEqualsPattern(root.get(criteria.getField()), criteria.getValue(), cb));
                    case LIKE -> predicate = cb.and(predicate, predicateStrategy.getLikePattern(root.get(criteria.getField()), criteria.getValue(), cb));
                    case RIGHT_LIMIT -> predicate = cb.and(predicateStrategy.getRightLimitPattern(root.get(criteria.getField()), criteria.getValue(), cb));
                    case LEFT_LIMIT -> predicate = cb.and(predicateStrategy.getLeftLimitPattern(root.get(criteria.getField()), criteria.getValue(), cb));
            }
        }
        cq.where(predicate);
        TypedQuery<WarehouseGood> query = entityManager.createQuery(cq);
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        return query.getResultList();
    }

    private static PredicateStrategy getPredicateStrategy(SearchCriteria<?> criteria) throws Exception {
        PredicateStrategy predicateStrategy;
        if (criteria.getValue() instanceof String) {
            predicateStrategy = new StringPredicateStrategy();;
        } else if (criteria.getValue() instanceof BigDecimal) {
            predicateStrategy = new BigDecimalPredicateStrategy();
        } else if (criteria.getValue() instanceof LocalDateTime) {
            predicateStrategy = new LocalDatePredicateStrategy();
        }else {
            throw new Exception("Wrong Type");
        }
        return predicateStrategy;
    }
}
