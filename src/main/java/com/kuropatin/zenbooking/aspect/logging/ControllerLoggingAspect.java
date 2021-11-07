package com.kuropatin.zenbooking.aspect.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Log4j2
public final class ControllerLoggingAspect {

    @Around("execution(* com.kuropatin.zenbooking.controller.*.*(..))")
    public Object logAroundMethods(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String baseMethod = "Method " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
        final StopWatch timer = new StopWatch();
        timer.start();
        final Object proceed = joinPoint.proceed();
        timer.stop();
        log.trace(baseMethod + " finished in " + timer.getTotalTimeMillis() + " ms");
        return proceed;
    }}