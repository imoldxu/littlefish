package com.x.commons.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.x.context.SpringContextUtil;
import com.x.service.AliYunOssService;

public class XJSONStringSerializer  implements ObjectSerializer{

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		
		if(object instanceof String ) {
			String jsonStr = (String)object;
			if(jsonStr.trim().startsWith("{")) { 
				JSONObject obj = JSONObject.parseObject(jsonStr);
			
				serializer.write(obj);
			}else if(jsonStr.trim().startsWith("[")) {
				JSONArray array = JSONObject.parseArray(jsonStr);
				
				serializer.write(array);
			}else {
				throw new JSONException("这不是一个JSON字符串");
			}
		}
	}

}

