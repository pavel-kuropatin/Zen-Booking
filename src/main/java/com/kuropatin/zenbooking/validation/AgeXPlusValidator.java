package com.kuropatin.zenbooking.validation;

import com.kuropatin.zenbooking.util.ApplicationTimeUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public final class AgeXPlusValidator implements ConstraintValidator<AgeXPlus, LocalDate> {

    private int minAge;

    @Override
    public void initialize(AgeXPlus annotation) {
        minAge = annotation.minAge();
    }

    @Override
    public boolean isValid(final LocalDate date, final ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        return date.plusYears(minAge).isBefore(ApplicationTimeUtils.getLocalDate()) || date.plusYears(minAge).isEqual(ApplicationTimeUtils.getLocalDate());
    }
}