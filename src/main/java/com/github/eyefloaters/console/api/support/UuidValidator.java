package com.github.eyefloaters.console.api.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import org.apache.kafka.common.Uuid;

public class UuidValidator implements ConstraintValidator<UuidValidator.ValidUuid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Uuid.fromString(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = UuidValidator.class)
    @Documented
    public @interface ValidUuid {
        String message();

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        ErrorCategory category();

    }
}