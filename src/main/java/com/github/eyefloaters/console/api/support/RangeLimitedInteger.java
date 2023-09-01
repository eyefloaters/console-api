package com.github.eyefloaters.console.api.support;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Digits(integer = 19, fraction = 0)
@DecimalMin(value = "1", inclusive = true)
@DecimalMax(value = "9223372036854775807", inclusive = true)
@ReportAsSingleViolation
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface RangeLimitedInteger {

    String message() default "must be an integer between {min} and {max}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ErrorCategory category();

    String source() default "";

    @OverridesAttribute(constraint = DecimalMin.class, name = "value")
    String min() default "1";

    @OverridesAttribute(constraint = DecimalMax.class, name = "value")
    String max() default "9223372036854775807";

}
