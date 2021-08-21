package com.kuropatin.bookingapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * The annotated element must not be {@code null} and must be an {@code short} in range (including) between {@code min} and {@code max}.
 * <p>
 * Accepts {@code CharSequence}.
 */
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ShortInRangeValidator.class)
public @interface ShortInRange {

    /**
     * @return the minimum value of annotated element
     */
    short min();

    /**
     * @return the maximum value of annotated element
     */
    short max();

    /**
     * @return the error message template
     */
    String message() default "Number must be integer in specified range";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};
}