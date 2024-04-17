package com.warehousesystem.app.annotation.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TransactionAspect {

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object measureTransactionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final String methodName = proceedingJoinPoint.getSignature().getName();
        final String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        final StopWatch stopWatch = new StopWatch();
        System.out.println("Starting @Transactional time measurement for " + className +" Method name " + methodName);
        stopWatch.start();

        TransactionSynchronizationManager
                .registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        stopWatch.stop();
                        System.out.println("@Transactional execution time of " + className +" Method name " + methodName +" is " + stopWatch.getTotalTimeMillis() + " ms");
                    }
                });

        return proceedingJoinPoint.proceed();
    }

}