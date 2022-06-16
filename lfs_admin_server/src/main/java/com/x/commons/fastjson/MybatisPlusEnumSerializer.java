package com.x.commons.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.baomidou.mybatisplus.annotation.IEnum;

public class MybatisPlusEnumSerializer implements ObjectSerializer, ObjectDeserializer{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		final int token = parser.lexer.token();
		
		Class enumClass = (Class)type;
		Object[] enumConstants = enumClass.getEnumConstants();
		if(IEnum.class.isAssignableFrom(enumClass)) {
			for(Object enumConstant: enumConstants) {
				IEnum<Integer> iEnum = (IEnum<Integer>)enumConstant;
				int value = Integer.MAX_VALUE;
				if(token == JSONToken.LITERAL_INT) {
					value = parser.lexer.intValue();
				}else if(token == JSONToken.LITERAL_STRING) {
					value = Integer.parseInt(parser.lexer.stringVal());
				}
				if(value == iEnum.getValue()) {
					return (T)iEnum;
				}
			}
			throw new JSONException("没有匹配的枚举");
		}else {
			//普通的枚举
			if(token == JSONToken.LITERAL_INT) {
				int intValue = parser.lexer.intValue();
				parser.lexer.nextToken(JSONToken.COMMA);
				if(intValue<0 || intValue>enumConstants.length) {
					throw new JSONException("没有匹配的枚举");
				}
				return (T)enumConstants[intValue];
			} else if(token == JSONToken.LITERAL_STRING){
				return (T)Enum.valueOf(enumClass, parser.lexer.stringVal());
			}
		}
		return null;
	}

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_STRING;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		if(object instanceof IEnum) {
			IEnum iEnum = (IEnum)object;
			serializer.write(iEnum.getValue());
		} else {
			serializer.write(object);
		}
	}

}
