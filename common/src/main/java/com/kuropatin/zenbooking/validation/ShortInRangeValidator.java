package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class ShortInRangeValidator implements ConstraintValidator<ShortInRange, Short> {

    private short min;
    private short max;

    @Override
    public void initialize(ShortInRange annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(final Short value, final ConstraintValidatorContext constraintValidatorContext) {
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