package com.warehousesystem.app.scheduling.conditions;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class SchedulingAndNotOptimizedCondition extends AllNestedConditions {

    public SchedulingAndNotOptimizedCondition() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty(value="app.scheduling.optimization", havingValue = "false")
    static class OptimizationEnabled {

    }

    @ConditionalOnProperty(value = "app.scheduling.enabled", havingValue = "true")
    static class SchedulingEnabled {

    }

}
