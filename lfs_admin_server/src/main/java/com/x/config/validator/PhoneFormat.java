package com.x.config.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhouzhixuan
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PhoneFormatValidator.class)
public @interface PhoneFormat {
    String message() default "请输入正确的手机号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
