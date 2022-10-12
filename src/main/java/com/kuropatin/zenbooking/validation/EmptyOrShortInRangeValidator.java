package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class EmptyOrShortInRangeValidator implements ConstraintValidator<EmptyOrShortInRange, Short> {

    private int min;
    private int max;

    @Override
    public void initialize(EmptyOrShortInRange annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(final Short value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.toString().isEmpty()) {
            return true;
        }
        try {
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}