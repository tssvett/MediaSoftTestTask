package com.warehousesystem.app.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class IntegerPredicateStrategy implements PredicateStrategy<Integer> {

    @Override
    public Predicate getEqualsPattern(Expression<Integer> expression, Integer value, CriteriaBuilder cb) {
        return cb.equal(expression, value);
    }

    @Override
    public Predicate getRightLimitPattern(Expression<Integer> expression, Integer value, CriteriaBuilder cb) {
        return cb.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLeftLimitPattern(Expression<Integer> expression, Integer value, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<Integer> expression, Integer value, CriteriaBuilder cb) {
        return cb.and(
                cb.greaterThanOrEqualTo(expression, value - 10),
                cb.lessThanOrEqualTo(expression, value + 10)
        );
    }
}
