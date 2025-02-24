package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.PersonName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PersonNameValidator implements ConstraintValidator<PersonName, String> {

    private PersonName currentAnnotation;

    @Override
    public void initialize(PersonName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.currentAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return currentAnnotation.nullable();
        }
        return value.matches("(\\p{L})+(\\p{L}|\\s|\\.)*");
    }
}
