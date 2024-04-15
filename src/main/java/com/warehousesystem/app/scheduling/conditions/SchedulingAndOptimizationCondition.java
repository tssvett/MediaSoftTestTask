package com.warehousesystem.app.scheduling.conditions;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

public class SchedulingAndOptimizationCondition extends AllNestedConditions {

    public SchedulingAndOptimizationCondition() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty(value = "app.scheduling.enabled", havingValue = "true")
    static class SchedulingEnabled {

    }

    @ConditionalOnProperty(value = "app.scheduling.optimization", havingValue = "true")
    static class OptimizationEnabled {

    }
}
