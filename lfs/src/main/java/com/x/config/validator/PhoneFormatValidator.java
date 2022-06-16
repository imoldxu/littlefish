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
public class PhoneFormatValidator implements ConstraintValidator<PhoneFormat, String> {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        return PHONE_PATTERN.matcher(s).matches();
    }
}
