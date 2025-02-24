package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.Username;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UsernameValidatorTest {

    private final ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    private final UsernameValidator usernameValidator = new UsernameValidator();
    private final Username annotation = Mockito.mock(Username.class);

    @BeforeEach
    public void initMocks() {
        Mockito.when(annotation.nullable()).thenReturn(false);
        Mockito.when(annotation.allowedSpecialChars()).thenReturn("_.");
        Mockito.when(annotation.minLength()).thenReturn(5);
        Mockito.when(annotation.allowUppercase()).thenReturn(false);
        usernameValidator.initialize(annotation);
    }

    @Test
    public void isValid_ShouldBeValidIfUsernameIsNullIfSetNullable() {
        // GIVEN
        String userName = null;
        Mockito.when(annotation.nullable()).thenReturn(true);
        usernameValidator.initialize(annotation);
        // THEN
        Assertions.assertTrue(usernameValidator.isValid(userName, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfUsernameIsNullByDefault() {
        // GIVEN
        String userName = null;
        // THEN
        Assertions.assertFalse(usernameValidator.isValid(userName, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfUsernameIsBlank() {
        Assertions.assertFalse(usernameValidator.isValid("", constraintValidatorContext));
        Assertions.assertFalse(usernameValidator.isValid("  ", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfLengthIsLessThanMinLength() {
        Assertions.assertFalse(usernameValidator.isValid("john", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsNotAllowedSpecialChars() {
        Assertions.assertFalse(usernameValidator.isValid("john.doe#", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsUppercaseLetterByDefault() {
        Assertions.assertFalse(usernameValidator.isValid("John.doe", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfCUppercaseLetterIsAllowed() {
        Mockito.when(annotation.allowUppercase()).thenReturn(true);
        usernameValidator.initialize(annotation);
        Assertions.assertTrue(usernameValidator.isValid("John.doe", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfOnlyContainsAllowedChars() {
        Mockito.when(annotation.allowedSpecialChars()).thenReturn(".!#");
        usernameValidator.initialize(annotation);
        Assertions.assertFalse(usernameValidator.isValid("john.doe!#%", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldNotAllowAnySpecialCharsWhenSetNull() {
        Mockito.when(annotation.allowedSpecialChars()).thenReturn(null);
        usernameValidator.initialize(annotation);
        Assertions.assertFalse(usernameValidator.isValid("john.doe_1", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidForUnicodeChars() {
        Assertions.assertFalse(usernameValidator.isValid("john.dóe_1", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidWithDefaultOptions() {
        Assertions.assertTrue(usernameValidator.isValid("john.doe_1", constraintValidatorContext));
    }
}
