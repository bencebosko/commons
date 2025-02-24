package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.Username;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    private Username currentAnnotation;

    @Override
    public void initialize(Username constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.currentAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return currentAnnotation.nullable();
        }
        if (value.length() < currentAnnotation.minLength()) {
            return false;
        }
        var allowedSpecialChars = Objects.nonNull(currentAnnotation.allowedSpecialChars()) ? Objects.requireNonNull(currentAnnotation.allowedSpecialChars()) : "";
        var upperCaseChars = currentAnnotation.allowUppercase() ? "A-Z" : "";
        return value.matches("^([a-z0-9" + upperCaseChars + allowedSpecialChars + "])+$");
    }
}
