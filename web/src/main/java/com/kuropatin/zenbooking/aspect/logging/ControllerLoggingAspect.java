package com.kuropatin.zenbooking.aspect.logging;

import com.kuropatin.zenbooking.aspect.logging.utils.LoggingUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public final class ControllerLoggingAspect {

    @Around("execution(* com.kuropatin.zenbooking.controller.*.*(..))")
    public Object logAroundMethods(final ProceedingJoinPoint joinPoint) {
        return LoggingUtils.logAround(joinPoint);
    }
}