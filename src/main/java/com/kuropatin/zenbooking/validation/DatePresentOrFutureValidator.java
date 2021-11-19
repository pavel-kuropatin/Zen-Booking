package com.kuropatin.zenbooking.validation;

import com.kuropatin.zenbooking.util.ApplicationTimeUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class DatePresentOrFutureValidator implements ConstraintValidator<DatePresentOrFuture, CharSequence> {

    @Override
    public boolean isValid(final CharSequence value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        try {
            final LocalDate date = LocalDate.parse(value);
            //TODO: refactor in future to check date in property location time zone if needed
            return date.isEqual(ApplicationTimeUtils.getLocalDate()) || date.isAfter(ApplicationTimeUtils.getLocalDate());
        } catch (DateTimeParseException e){
            return false;
        }
    }
}