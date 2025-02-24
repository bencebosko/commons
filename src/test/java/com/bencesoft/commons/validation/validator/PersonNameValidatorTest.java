package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.PersonName;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PersonNameValidatorTest {

    private final ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    private final PersonNameValidator personNameValidator = new PersonNameValidator();
    private final PersonName annotation = Mockito.mock(PersonName.class);

    @BeforeEach
    public void initMocks() {
        Mockito.when(annotation.nullable()).thenReturn(false);
        personNameValidator.initialize(annotation);
    }

    @Test
    public void isValid_ShouldBeValidIfNameIsNullIfSetNullable() {
        // GIVEN
        String personName = null;
        Mockito.when(annotation.nullable()).thenReturn(true);
        personNameValidator.initialize(annotation);
        // THEN
        Assertions.assertTrue(personNameValidator.isValid(personName, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfNameIsNullByDefault() {
        // GIVEN
        String personName = null;
        Mockito.when(annotation.nullable()).thenReturn(false);
        personNameValidator.initialize(annotation);
        // THEN
        Assertions.assertFalse(personNameValidator.isValid(personName, constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfNameIsBlank() {
        Assertions.assertFalse(personNameValidator.isValid("", constraintValidatorContext));
        Assertions.assertFalse(personNameValidator.isValid("   ", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfFirstCharIsNotLetter() {
        Assertions.assertFalse(personNameValidator.isValid(".", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainSpecialChar() {
        Assertions.assertFalse(personNameValidator.isValid("Name!", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeInvalidIfContainsNumber() {
        Assertions.assertFalse(personNameValidator.isValid("Name123", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidIfContainsLettersWithDot() {
        Assertions.assertTrue(personNameValidator.isValid("K. Péter", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidForUnicodeChars() {
        Assertions.assertTrue(personNameValidator.isValid("János", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldBeValidWithWhitespaceBetweenLetters() {
        Assertions.assertTrue(personNameValidator.isValid("Ákos Mátyás", constraintValidatorContext));
    }
}
