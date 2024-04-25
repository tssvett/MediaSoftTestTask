package com.warehousesystem.app.search.strategy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.warehousesystem.app.search.criteria.StringSearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;


public interface PredicateStrategy<T> {

    Predicate getEqualsPattern(Expression<T> expression, T value, CriteriaBuilder cb);

    Predicate getRightLimitPattern(Expression<T> expression, T value, CriteriaBuilder cb);

    Predicate getLeftLimitPattern(Expression<T> expression, T value, CriteriaBuilder cb);

    Predicate getLikePattern(Expression<T> expression, T value, CriteriaBuilder cb);


}
