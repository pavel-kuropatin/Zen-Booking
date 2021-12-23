package com.kuropatin.zenbooking.aspect.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public final class ServiceExceptionLoggingAspect {

    @AfterThrowing(pointcut = "execution(* com.kuropatin.zenbooking.service.*.*(..))", throwing = "exception")
    public void afterThrowingPointcut(JoinPoint joinPoint, Throwable exception) {
        final String baseMethod = "Method " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
        final String exceptionDescription = exception.getClass().getSimpleName() + ": " + exception.getMessage();
        log.warn(baseMethod + " throws " + exceptionDescription);
    }
}