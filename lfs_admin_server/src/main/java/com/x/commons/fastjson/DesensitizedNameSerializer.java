package com.x.commons.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

public class DesensitizedNameSerializer implements ObjectSerializer {

	private static String desensitizedName(String fullName){
        if (!StringUtils.isNoneBlank(fullName)) {
            String name = StringUtils.left(fullName, 1);
            return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
        }
        return fullName;
    }

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		String name = (String) object;
        String desensitized = desensitizedName(name);
        serializer.write(desensitized);
	}
	
}
