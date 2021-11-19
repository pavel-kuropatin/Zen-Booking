package com.kuropatin.zenbooking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

    private Set<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(final CharSequence value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        return acceptedValues.contains(value.toString());
    }
}