package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class EmptyOrBiggerValidator implements ConstraintValidator<EmptyOrBigger, CharSequence> {

    private int min;

    @Override
    public void initialize(EmptyOrBigger annotation) {
        min = annotation.min();
    }

    @Override
    public boolean isValid(final CharSequence value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.toString().isEmpty()) {
            return true;
        }
        try {
            final int integer = Integer.parseInt(value.toString());
            return integer > min;
        } catch (NumberFormatException e){
            return false;
        }
    }
}