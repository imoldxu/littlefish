package com.x.config.validator;

import org.springframework.context.annotation.Configuration;

import com.x.commons.util.IdCardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author xujh
 */
@Configuration
public class IDCardFormatValidator implements ConstraintValidator<IDCardFormat, String> {
    
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return IdCardUtil.isIDCard(s);
    }
}
