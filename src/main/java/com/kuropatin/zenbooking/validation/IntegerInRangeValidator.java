package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerInRangeValidator implements ConstraintValidator<IntegerInRange, CharSequence> {

    private int min;
    private int max;

    @Override
    public void initialize(IntegerInRange annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        try {
            int integer = Integer.parseInt(value.toString());
            return integer >= min && integer <= max;
        } catch (NumberFormatException e){
            return false;
        }
    }
}