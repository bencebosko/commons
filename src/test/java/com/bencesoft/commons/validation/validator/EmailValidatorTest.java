package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.Email;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EmailValidatorTest {

    private final ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    private final EmailValidator emailValidator = new EmailValidator();
    private final Email annotation = Mockito.mock(Email.class);

    @BeforeEach
    public void initMocks() {
        Mockito.when(annotation.nullable()).thenReturn(false);
        Mockito.when(annotation.allowedSpecialChars()).thenReturn("._-");
        emailValidator.initialize(annotation);
    }

    @Test
    public void isValid_ShouldBeValidForNullIfSetNullable() {
        // GIVEN
        String email = null;
        Mockito.when(annotation.nullable()).thenReturn(true);
        emailValidator.initialize(annotation);
        // THEN
        Assertions.assertTrue(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfEmailIsNullByDefault() {
        // GIVEN
        String email = null;
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfLocalPartIsEmpty() {
        // GIVEN
        String email = "@gmail.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfDomainIsEmpty() {
        // GIVEN
        String email = "user@.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfDomainEndIsEmpty() {
        // GIVEN
        String email = "user@gmail.";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfLocalPartContainsInvalidChars() {
        // GIVEN
        var email = "user!@gmail.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfLocalPartContainsDoubleDots() {
        // GIVEN
        var email = "..user@gmail.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldNotAllowAnySpecialCharsIfSetNull() {
        // GIVEN
        var email = "user_@gmail.com";
        Mockito.when(annotation.allowedSpecialChars()).thenReturn(null);
        emailValidator.initialize(annotation);
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfLocalPartContainsOnlyAllowedChars() {
        // GIVEN
        var allowedSpecialChars = "!#";
        var email = "#user!@gmail.com";
        Mockito.when(annotation.allowedSpecialChars()).thenReturn(allowedSpecialChars);
        emailValidator.initialize(annotation);
        // THEN
        Assertions.assertTrue(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfNotContainsAtSign() {
        // GIVEN
        var email = "user.gmail.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsMoreAtSign() {
        // GIVEN
        var email = "us@er@gmail.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfDomainContainsInvalidChar() {
        // GIVEN
        var email = "user@gmail_.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfDomainContainsDoubleDots() {
        // GIVEN
        var email = "user@gmail..com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfDomainEndInvalid() {
        // GIVEN
        var email = "user@gmail.c";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfDomainNotContainsDot() {
        // GIVEN
        var email = "user@gmailcom";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsUppercase() {
        // GIVEN
        var email = "john.doe-123_@Gmail-123.sub.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidForUnicodeChars() {
        // GIVEN
        var email = "john.dóe-123_@Gmail-123.súb.com";
        // THEN
        Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidWithDefaultOptions() {
        // GIVEN
        var email = "john.doe-123_@gmail-123.sub.com";
        // THEN
        Assertions.assertTrue(emailValidator.isValid(email, constraintValidatorContext));
    }
}
