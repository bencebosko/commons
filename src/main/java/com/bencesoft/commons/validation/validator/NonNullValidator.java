package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.NonNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NonNullValidator implements ConstraintValidator<NonNull, Object> {

    @Override
    public void initialize(NonNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(value);
    }
}
