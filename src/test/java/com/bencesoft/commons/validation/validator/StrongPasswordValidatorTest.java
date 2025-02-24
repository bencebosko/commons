package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.StrongPassword;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StrongPasswordValidatorTest {

    private final ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    private final StrongPasswordValidator strongPasswordValidator = new StrongPasswordValidator();
    private final StrongPassword annotation = Mockito.mock(StrongPassword.class);

    @BeforeEach
    public void initMocks() {
        Mockito.when(annotation.nullable()).thenReturn(false);
        Mockito.when(annotation.minLength()).thenReturn(8);
        Mockito.when(annotation.needUppercase()).thenReturn(true);
        Mockito.when(annotation.needLowercase()).thenReturn(true);
        Mockito.when(annotation.needDigit()).thenReturn(true);
        Mockito.when(annotation.needSpecialChar()).thenReturn(true);
        Mockito.when(annotation.allowedSpecialChars()).thenReturn("._-!?#@&%*");
        strongPasswordValidator.initialize(annotation);
    }

    @Test
    public void isValid_ShouldBeValidIfPasswordIsNullIfSetNullable() {
        // GIVEN
        String password = null;
        Mockito.when(annotation.nullable()).thenReturn(true);
        strongPasswordValidator.initialize(annotation);
        // THEN
        Assertions.assertTrue(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfPasswordIsNullByDefault() {
        // GIVEN
        String password = null;
        // THEN
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfPasswordIsTooShort() {
        var password = "John12.";
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsNoUppercaseByDefault() {
        var password = "johndoe123.";
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfUppercaseIsNotNeeded() {
        var password = "johndoe123.";
        Mockito.when(annotation.needUppercase()).thenReturn(false);
        strongPasswordValidator.initialize(annotation);
        Assertions.assertTrue(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsNoLowercaseByDefault() {
        var password = "JOHNDOE123.";
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfLowercaseIsNotNeeded() {
        var password = "JOHNDOE123.";
        Mockito.when(annotation.needLowercase()).thenReturn(false);
        strongPasswordValidator.initialize(annotation);
        Assertions.assertTrue(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsNoDigitByDefault() {
        var password = "JohnDoeNoDigit.";
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfDigitIsNotNeeded() {
        var password = "JohnDoeNoDigit.";
        Mockito.when(annotation.needDigit()).thenReturn(false);
        Mockito.when(annotation.allowedSpecialChars()).thenReturn(".");
        strongPasswordValidator.initialize(annotation);
        Assertions.assertTrue(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsNoSpecialCharByDefault() {
        var password = "JohnDoe123";
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfNoSpecialCharIsNeeded() {
        var password = "JohnDoe123";
        Mockito.when(annotation.needSpecialChar()).thenReturn(false);
        strongPasswordValidator.initialize(annotation);
        Assertions.assertTrue(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsNotAllowedSpecialChar() {
        var password = "JohnDoe123#";
        Mockito.when(annotation.allowedSpecialChars()).thenReturn(".!?");
        strongPasswordValidator.initialize(annotation);
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldNotAllowAnySpecialCharsIfSetNull() {
        var password = "JohnDoe123.";
        Mockito.when(annotation.allowedSpecialChars()).thenReturn(null);
        strongPasswordValidator.initialize(annotation);
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsWhitespace() {
        var password = "JohnDoe123. ";
        Assertions.assertFalse(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidWithDefaultOptions() {
        var password = "JohnDoe123.";
        Assertions.assertTrue(strongPasswordValidator.isValid(password, constraintValidatorContext));
    }
}
