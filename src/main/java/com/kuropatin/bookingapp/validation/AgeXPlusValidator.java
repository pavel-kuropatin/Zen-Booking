package com.kuropatin.bookingapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AgeXPlusValidator implements ConstraintValidator<AgeXPlus, CharSequence> {

    private int minAge;

    @Override
    public void initialize(AgeXPlus annotation) {
        minAge = annotation.minAge();
    }

    @Override
    public boolean isValid(CharSequence date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        try {
            LocalDate birthDate = LocalDate.parse(date);
            return birthDate.plusYears(minAge).isBefore(LocalDate.now()) || birthDate.plusYears(minAge).isEqual(LocalDate.now());
        } catch (DateTimeParseException e){
            return false;
        }
    }
}