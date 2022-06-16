package com.x.commons.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

/**
 * 15,18位身份证脱敏
 * @author 老徐
 *
 */
public class DesensitizedIDNumberSerializer implements ObjectSerializer {

	private static String desensitizedIdNumber(String idNumber){
        if (!StringUtils.isBlank(idNumber)) {
            if (idNumber.length() == 15){
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
            }
            if (idNumber.length() == 18){
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
            }
        }
        return idNumber;
    }

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		String idNumber = (String) object;
        String desensitized = desensitizedIdNumber(idNumber);
        serializer.write(desensitized);
	}
	
}
