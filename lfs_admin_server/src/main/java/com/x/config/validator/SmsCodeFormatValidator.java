package com.x.config.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author zhouzhixuan
 */
@Configuration
public class SmsCodeFormatValidator implements ConstraintValidator<SmsCodeFormat, String> {
    private static final Pattern SMS_CODE_PATTERN = Pattern.compile("\\d{6}");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        return SMS_CODE_PATTERN.matcher(s).matches();
    }
}
