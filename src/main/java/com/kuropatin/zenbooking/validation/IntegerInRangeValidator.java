package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class IntegerInRangeValidator implements ConstraintValidator<IntegerInRange, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(IntegerInRange annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(final Integer value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        try {
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}