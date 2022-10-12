package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class EmptyOrBiggerValidator implements ConstraintValidator<EmptyOrBigger, Integer> {

    private int min;

    @Override
    public void initialize(EmptyOrBigger annotation) {
        min = annotation.min();
    }

    @Override
    public boolean isValid(final Integer value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.toString().isEmpty()) {
            return true;
        }
        try {
            return value > min;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}