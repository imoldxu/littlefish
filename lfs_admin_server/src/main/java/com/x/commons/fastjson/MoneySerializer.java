package com.x.commons.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.x.commons.util.MoneyUtil;

/**
   * 实现金额的转换，支持fastJson的JSONField来配置格式化转换
 * @author 老徐
 *
 */
public class MoneySerializer implements ObjectSerializer, ObjectDeserializer{ 
    
	@SuppressWarnings("unchecked")
	@Override
	public <T> T  deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		String value = parser.parseObject(String.class);
        return (T) MoneyUtil.changeY2F(value);
	}

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}
    
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		Integer amount = (Integer) object;
        String amountOfYuanStr = MoneyUtil.changeF2Y(amount);
        serializer.write(amountOfYuanStr);
	}  
	
}
