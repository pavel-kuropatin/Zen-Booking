package com.kuropatin.bookingapp.aspect.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class RepositoryLoggingAspect {

    private static final Logger log = Logger.getLogger(RepositoryLoggingAspect.class);

    @Around("execution(* com.kuropatin.bookingapp.repository.*.*(..))")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String baseMethod = "Method " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
        log.trace(baseMethod + " started");
        StopWatch timer = new StopWatch();
        timer.start();
        Object proceed = joinPoint.proceed();
        timer.stop();
        log.trace(baseMethod + " finished in " + timer.getTotalTimeMillis() + " ms");
        return proceed;
    }
}
