package com.x.config.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author xujh
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IDCardFormatValidator.class)
public @interface IDCardFormat {
    String message() default "请输入正确的身份证号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
