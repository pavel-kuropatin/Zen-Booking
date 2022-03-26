package com.kuropatin.zenbooking.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * The annotated element must not be {@code null} and must be a valid {@code date} type in format {@code yyyy-mm-dd}. Difference between today date and annotated element date must be at least
 * {@code minAge} years.
 * <p>
 * Accepts {@code CharSequence}.
 */
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NullableBooleanValidator.class)
public @interface NullableBoolean {

    /**
     * @return the error message template
     */
    String message() default "Should be empty or a boolean value true or false";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};
}