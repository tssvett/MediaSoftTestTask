package com.warehousesystem.app.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;

public class LocalDateTimePredicateStrategy implements PredicateStrategy<LocalDateTime> {

    @Override
    public Predicate getEqualsPattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder cb) {
        return cb.equal(cb.function("date", LocalDateTime.class,expression), value.toLocalDate());
    }

    @Override
    public Predicate getRightLimitPattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder cb) {
        return cb.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLeftLimitPattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder cb) {
        return cb.and(
                cb.greaterThanOrEqualTo(expression, value.minusDays(3)),
                cb.lessThanOrEqualTo(expression, value.plusDays(3))
        );
    }
}
