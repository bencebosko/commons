package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private Email currentValidation;

    @Override
    public void initialize(Email constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.currentValidation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return currentValidation.nullable();
        }
        if (value.matches(".*(\\.\\.).*") || value.matches(".*(@.*@).*")) {
            return false;
        }
        var allowedSpecialChars = Objects.nonNull(currentValidation.allowedSpecialChars()) ? currentValidation.allowedSpecialChars() : "";
        return value.matches("^([a-z0-9" + allowedSpecialChars + "])+@([a-z0-9.-]+)\\.([a-z]{2,3})$");
    }
}
