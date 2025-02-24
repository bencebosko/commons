package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.NotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {

    private NotBlank currentAnnotation;

    @Override
    public void initialize(NotBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        currentAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return currentAnnotation.nullable();
        }
        return !value.isBlank();
    }
}
