package com.warehousesystem.app.annotation.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class NonTransactionalAspect {

    @Around(value = "@annotation(com.warehousesystem.app.annotation.MeasureWorkingTime)")
    public Object nonTransactionalExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final String methodName = proceedingJoinPoint.getSignature().getName();
        final String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        final StopWatch stopWatch = new StopWatch();
        log.info("Starting @MeasureWorkingTime time measurement for " + className + " Method name " + methodName);
        stopWatch.start();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("@MeasureWorkingTime execution time of " + className + " Method name " + methodName + " is " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }
}
