package com.x.commons.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.x.context.SpringContextUtil;
import com.x.service.AliYunOssService;

/**
   * 实现oss的转换，支持fastJson的JSONField来配置格式化转换
 * @author 老徐
 *
 */
public class OssSerializer implements ObjectSerializer, ObjectDeserializer{

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
		AliYunOssService ossService = applicationContext.getBean(AliYunOssService.class);
		if(object instanceof List ) {
			List list = (List)object;
			Object ossImgList = list.stream().map(img->{
				String ossImgSrc = ossService.signAccessObject((String) img);
				return ossImgSrc;
			}).collect(Collectors.toList());
			
	        serializer.write(ossImgList);;
		}else {
			String text = ossService.signAccessObject((String) object);
	        serializer.write(text);
		}
	}

	@Override
	public Object deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		if(type instanceof List) {
			List<String> arrays = parser.parseArray(String.class);
			List<String> urls = arrays.stream().map(rawValue->{
				try {
					URL url = new URL(rawValue);
					String uri = url.getPath();
			        return uri;
				} catch (MalformedURLException e) {
					return rawValue; //若不是一个url，则认为直接就是uri
				}
			}).collect(Collectors.toList());
			return urls;
		}else {
			String rawValue = parser.parseObject(String.class);
			try {
				URL url = new URL(rawValue);
				String uri = url.getPath();
		        return uri;
			} catch (MalformedURLException e) {
				return rawValue; //若不是一个url，则认为直接就是uri
			}
		}
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}  
	
}
