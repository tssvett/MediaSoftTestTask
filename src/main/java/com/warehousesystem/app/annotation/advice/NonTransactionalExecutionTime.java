package com.warehousesystem.app.annotation.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NonTransactionalExecutionTime {

    @Around(value = "@annotation(com.warehousesystem.app.annotation.NonTransactionalExecutionTime)")
    public Object nonTransactionalExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time of " + proceedingJoinPoint.getSignature().getDeclaringTypeName() +" Method name " +   proceedingJoinPoint.getSignature().getName() +" is " + (endTime - startTime) + " ms");
        return object;
    }
}
