package com.kuropatin.zenbooking.aspect.logging;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public final class ControllerLoggingAspect {

//    @Around("execution(* com.kuropatin.zenbooking.controller.*.*(..))")
//    public Object logAroundMethods(final ProceedingJoinPoint joinPoint) {
//        return LoggingUtils.logAround(joinPoint);
//    }
}