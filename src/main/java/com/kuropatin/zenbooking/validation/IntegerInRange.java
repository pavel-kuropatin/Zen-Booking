package com.kuropatin.zenbooking.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * The annotated element must not be {@code null} and must be an {@code integer} in range (including) between {@code min} and {@code max}.
 * <p>
 * Accepts {@code CharSequence}.
 */
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegerInRangeValidator.class)
public @interface IntegerInRange {

    /**
     * @return the minimum value of annotated element
     */
    int min();

    /**
     * @return the maximum value of annotated element
     */
    int max();

    /**
     * @return the error message template
     */
    String message() default "Value must be integer in specified range";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};
}