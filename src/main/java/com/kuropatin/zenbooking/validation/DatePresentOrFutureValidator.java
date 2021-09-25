package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DatePresentOrFutureValidator implements ConstraintValidator<DatePresentOrFuture, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(value);
            return date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e){
            return false;
        }
    }
}