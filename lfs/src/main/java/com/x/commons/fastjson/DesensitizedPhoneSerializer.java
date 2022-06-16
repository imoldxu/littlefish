package com.x.commons.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

public class DesensitizedPhoneSerializer implements ObjectSerializer {

	private static String desensitizedPhoneNumber(String phoneNumber){
        if(StringUtils.isNotEmpty(phoneNumber)){
            phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }
        return phoneNumber;
    }

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		String phoneNumber = (String) object;
        String desensitized = desensitizedPhoneNumber(phoneNumber);
        serializer.write(desensitized);
	}
	
}
