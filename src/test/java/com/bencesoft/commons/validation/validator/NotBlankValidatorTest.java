package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.NotBlank;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NotBlankValidatorTest {

    private final ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    private final NotBlankValidator notBlankValidator = new NotBlankValidator();
    private final NotBlank annotation = Mockito.mock(NotBlank.class);

    @BeforeEach
    public void initMocks() {
        Mockito.when(annotation.nullable()).thenReturn(false);
        notBlankValidator.initialize(annotation);
    }

    @Test
    public void isValid_ShouldBeValidForNullIfSetNullable() {
        // GIVEN
        String value = null;
        Mockito.when(annotation.nullable()).thenReturn(true);
        notBlankValidator.initialize(annotation);
        // THEN
        Assertions.assertTrue(notBlankValidator.isValid(value, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidForNullByDefault() {
        // GIVEN
        String value = null;
        // THEN
        Assertions.assertFalse(notBlankValidator.isValid(value, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfStringIsEmpty() {
        Assertions.assertFalse(notBlankValidator.isValid("", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfOnlyContainsWhiteSpace() {
        Assertions.assertFalse(notBlankValidator.isValid("   ", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfStringIsNonEmpty() {
        Assertions.assertTrue(notBlankValidator.isValid("nonEmpty", constraintValidatorContext));
    }
}
