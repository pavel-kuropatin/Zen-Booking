package com.kuropatin.bookingapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShortInRangeValidator implements ConstraintValidator<ShortInRange, CharSequence> {

    private short min;
    private short max;

    @Override
    public void initialize(ShortInRange annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        try {
            short integer = Short.parseShort(value.toString());
            return integer >= min && integer <= max;
        } catch (NumberFormatException e){
            return false;
        }
    }
}