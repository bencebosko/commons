package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.NonNegative;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NonNegativeValidator implements ConstraintValidator<NonNegative, Number> {

    private NonNegative currentAnnotation;

    @Override
    public void initialize(NonNegative constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.currentAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return currentAnnotation.nullable();
        }
        return value.doubleValue() >= 0;
    }
}
