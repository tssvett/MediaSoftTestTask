package com.warehousesystem.app.search.strategy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class StringPredicateStrategy implements PredicateStrategy<String> {

    @Override
    public Predicate getEqualsPattern(Expression<String> expression, String value, CriteriaBuilder cb) {
        return cb.equal(expression, value);
    }

    @Override
    public Predicate getRightLimitPattern(Expression<String> expression, String value, CriteriaBuilder cb) {
        return cb.like(expression, "%" + value);
    }

    @Override
    public Predicate getLeftLimitPattern(Expression<String> expression, String value, CriteriaBuilder cb) {
        return cb.like(expression, value + "%");
    }

    @Override
    public Predicate getLikePattern(Expression<String> expression, String value, CriteriaBuilder cb) {
        return cb.like(cb.lower(expression), "%" + value.toLowerCase() + "%");
    }
}
