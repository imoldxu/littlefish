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
@Constraint(validatedBy = DateFormatValidator.class)
public @interface DateFormat {
    String message() default "请输入正确的时间格式如: yyyy-MM-dd HH:mm:ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
