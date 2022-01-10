package com.kuropatin.zenbooking.aspect.logging.utils;

import com.kuropatin.zenbooking.exception.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingUtils {

    public static Object logAround(final ProceedingJoinPoint joinPoint) {
        try {
            final String baseMethod = "Method " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
            final StopWatch timer = new StopWatch();
            timer.start();
            final Object proceed = joinPoint.proceed();
            timer.stop();
            log.trace(baseMethod + " finished in " + timer.getTotalTimeMillis() + " ms");
            return proceed;
        } catch (final Throwable e) {
            throw new ApplicationException(e.getClass() + e.getMessage());
        }
    }

    public static void logAfterThrowing(final JoinPoint joinPoint, final Throwable exception) {
        final String baseMethod = "Method " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
        final String exceptionDescription = exception.getClass().getSimpleName() + ": " + exception.getMessage();
        log.warn(baseMethod + " throws " + exceptionDescription);
    }
}