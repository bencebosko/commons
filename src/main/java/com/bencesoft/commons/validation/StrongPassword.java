package com.bencesoft.commons.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bencesoft.commons.validation.constants.ValidationErrorCodes;
import com.bencesoft.commons.validation.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default ValidationErrorCodes.STRONG_PASSWORD;

    boolean nullable() default false;

    int minLength() default 8;

    boolean needUppercase() default true;

    boolean needLowercase() default true;

    boolean needDigit() default true;

    boolean needSpecialChar() default true;

    String allowedSpecialChars() default "._-!?#@&%*^";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
