package com.example.micro.planner.utils.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log
public class LoggingAspect {
    @Around("execution(* com.example.micro.planner.todo.controller..*(..))")
    public Object logMethodName(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info(String.format("Executing %s.%s()", className, methodName));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        log.info(String.format("Method %s.%s() execution time - %s ms",
                className, methodName, stopWatch.getTotalTimeMillis()));

        return retVal;
    }
}
