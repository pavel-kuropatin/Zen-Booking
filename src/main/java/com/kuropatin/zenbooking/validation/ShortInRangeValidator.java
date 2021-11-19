package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class ShortInRangeValidator implements ConstraintValidator<ShortInRange, CharSequence> {

    private short min;
    private short max;

    @Override
    public void initialize(ShortInRange annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(final CharSequence value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        try {
            final short integer = Short.parseShort(value.toString());
            return integer >= min && integer <= max;
        } catch (NumberFormatException e){
            return false;
        }
    }
}