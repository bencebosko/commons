package com.bencesoft.commons.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NonNullValidatorTest {

    private final ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    private final NonNullValidator nonNullValidator = new NonNullValidator();

    @Test
    public void issValid_ShouldBeInvalidIfValueIsNull() {
        // GIVEN
        String value = null;
        // THEN
        Assertions.assertFalse(nonNullValidator.isValid(value, constraintValidatorContext));
    }

    @Test
    public void issValid_ShouldBeValidIfValueIsNonNull() {
        // GIVEN
        String value = "";
        // THEN
        Assertions.assertTrue(nonNullValidator.isValid(value, constraintValidatorContext));
    }
}
