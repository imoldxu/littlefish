package com.x.config.validator;

import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @author zhouzhixuan
 */
@Configuration
public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        sdf.parse(s);
	        return true;
        }catch (ParseException e) {
			return false;
		}
    }
}
