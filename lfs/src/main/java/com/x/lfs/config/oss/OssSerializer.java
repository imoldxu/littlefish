package com.x.lfs.config.oss;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.x.lfs.service.impl.AliYunOssService;

/**
   * 实现金额的转换，支持fastJson的JSONField来配置格式化转换
 * @author 老徐
 *
 */
public class OssSerializer implements ApplicationContextAware, ObjectSerializer{
    
	private ApplicationContext applicationContext;
	
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		AliYunOssService aliService = applicationContext.getBean(AliYunOssService.class);
		String text = aliService.signAccessObject((String) object);
        serializer.write(text);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}  
	
}
