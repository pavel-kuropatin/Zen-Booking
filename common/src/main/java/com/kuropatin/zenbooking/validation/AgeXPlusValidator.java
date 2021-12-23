package com.kuropatin.zenbooking.validation;

import com.kuropatin.zenbooking.util.ApplicationTimeUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class AgeXPlusValidator implements ConstraintValidator<AgeXPlus, CharSequence> {

    private int minAge;

    @Override
    public void initialize(AgeXPlus annotation) {
        minAge = annotation.minAge();
    }

    @Override
    public boolean isValid(final CharSequence date, final ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        try {
            final LocalDate birthDate = LocalDate.parse(date);
            return birthDate.plusYears(minAge).isBefore(ApplicationTimeUtils.getLocalDate()) || birthDate.plusYears(minAge).isEqual(ApplicationTimeUtils.getLocalDate());
        } catch (DateTimeParseException e){
            return false;
        }
    }
}