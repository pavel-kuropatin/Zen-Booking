package com.kuropatin.bookingapp.aspect.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceExceptionLoggingAspect {

    private static final Logger log = Logger.getLogger(ServiceExceptionLoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.kuropatin.bookingapp.service.*.*(..))", throwing = "exception")
    public void afterThrowingPointcut(JoinPoint joinPoint, Throwable exception) {
        String baseMethod = "Method " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
        String exceptionDescription = exception.getClass().getSimpleName() + ": " + exception.getMessage();
        log.error(baseMethod + " throws " + exceptionDescription);
    }
}
