package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class NullableBooleanValidator implements ConstraintValidator<NullableBoolean, Boolean> {

    @Override
    public boolean isValid(final Boolean value, final ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}