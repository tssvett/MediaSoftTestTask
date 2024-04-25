package com.warehousesystem.app.search.strategy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import java.util.UUID;

public class UUIDPredicateStrategy implements PredicateStrategy<UUID> {

    @Override
    public Predicate getEqualsPattern(Expression<UUID> expression, UUID value, CriteriaBuilder cb) {
        return cb.equal(expression, value);
    }

    @Override
    public Predicate getRightLimitPattern(Expression<UUID> expression, UUID value, CriteriaBuilder cb) {
        return cb.equal(expression, value);
    }

    @Override
    public Predicate getLeftLimitPattern(Expression<UUID> expression, UUID value, CriteriaBuilder cb) {
        return cb.equal(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<UUID> expression, UUID value, CriteriaBuilder cb) {
        return cb.equal(expression, value);
    }
}
