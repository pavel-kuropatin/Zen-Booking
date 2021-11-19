package com.kuropatin.zenbooking.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * The annotated element can be {@code null} or {@code empty} and must be an {@code integer} equal or bigger than {@code min}.
 * <p>
 * Accepts {@code CharSequence}.
 */
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmptyOrBiggerValidator.class)
public @interface EmptyOrBigger {

    /**
     * @return the minimum value of annotated element
     */
    int min();

    /**
     * @return the error message template
     */
    String message() default "Value must be empty or integer equal or bigger than specified value";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};
}