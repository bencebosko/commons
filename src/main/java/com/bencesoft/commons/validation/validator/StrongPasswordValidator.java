package com.bencesoft.commons.validation.validator;

import com.bencesoft.commons.validation.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    private StrongPassword currentAnnotation;

    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        currentAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(password)) {
            return currentAnnotation.nullable();
        }
        if (password.length() < currentAnnotation.minLength()) {
            return false;
        }
        final var allowedSpecialChars = getAllowedSpecialChars(currentAnnotation);
        var hasUppercase = false;
        var hasLowercase = false;
        var hasDigit = false;
        var hasSpecial = false;
        for (int idx = 0; idx < password.length() ; idx++) {
            final var ch = password.charAt(idx);
            if (Character.isWhitespace(ch)) {
                return false;
            } else if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (allowedSpecialChars.contains(ch)) {
                hasSpecial = true;
            } else {
                return false;
            }
        }
        return isStrongPassword(currentAnnotation, hasUppercase, hasLowercase, hasDigit, hasSpecial);
    }

    private Set<Character> getAllowedSpecialChars(StrongPassword currentAnnotation) {
        String specialChars = (Objects.nonNull(currentAnnotation.allowedSpecialChars()) ? currentAnnotation.allowedSpecialChars(): "");
        return specialChars.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    private boolean isStrongPassword(StrongPassword currentAnnotation, boolean hasUppercase, boolean hasLowercase, boolean hasDigit, boolean hasSpecial) {
        return (!currentAnnotation.needUppercase() || hasUppercase) &&
               (!currentAnnotation.needLowercase() || hasLowercase) &&
               (!currentAnnotation.needDigit() || hasDigit) &&
               (!currentAnnotation.needSpecialChar() || hasSpecial);
    }
}
