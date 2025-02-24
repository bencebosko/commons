package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.NonNegative;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NonNegativeValidatorTest {

    private final ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    private final NonNegativeValidator nonNegativeValidator = new NonNegativeValidator();
    private final NonNegative annotation = Mockito.mock(NonNegative.class);

    @BeforeEach
    public void initMocks() {
        Mockito.when(annotation.nullable()).thenReturn(false);
        nonNegativeValidator.initialize(annotation);
    }

    @Test
    public void issValid_ShouldBeValidIfValueIsNullIfSetNullable() {
        // GIVEN
        Double value = null;
        Mockito.when(annotation.nullable()).thenReturn(true);
        nonNegativeValidator.initialize(annotation);
        // THEN
        Assertions.assertTrue(nonNegativeValidator.isValid(value, constraintValidatorContext));
    }

    @Test
    public void issValid_ShouldBeInvalidIfValueIsNullByDefault() {
        // GIVEN
        Double value = null;
        // THEN
        Assertions.assertFalse(nonNegativeValidator.isValid(value, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfNumberIsNegative() {
        Assertions.assertFalse(nonNegativeValidator.isValid(-1 * Double.MAX_VALUE, constraintValidatorContext));
        Assertions.assertFalse(nonNegativeValidator.isValid(-1 * Long.MAX_VALUE, constraintValidatorContext));
        Assertions.assertFalse(nonNegativeValidator.isValid(-0.0001, constraintValidatorContext));
        Assertions.assertFalse(nonNegativeValidator.isValid(-1, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfNumberIsNonNegative() {
        Assertions.assertTrue(nonNegativeValidator.isValid(Double.MAX_VALUE, constraintValidatorContext));
        Assertions.assertTrue(nonNegativeValidator.isValid(Long.MAX_VALUE, constraintValidatorContext));
        Assertions.assertTrue(nonNegativeValidator.isValid(0.0001, constraintValidatorContext));
        Assertions.assertTrue(nonNegativeValidator.isValid(0, constraintValidatorContext));
        Assertions.assertTrue(nonNegativeValidator.isValid(-0, constraintValidatorContext));
    }
}
