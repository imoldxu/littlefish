package com.x.config.mvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 配置fastJson来实现http请求和响应的转换
 * @author 老徐
 *
 */
@Configuration
public class AliFastJsonConfig implements WebMvcConfigurer {

	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	//将fastjsonMessageConverter放在前面
		converters.add(0, fastJsonMessageConverter());
    }
	
    private HttpMessageConverter<?> fastJsonMessageConverter() {       
		FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
        		//格式化，会消耗性能
                SerializerFeature.PrettyFormat,
        		// 保留map空的字段
                SerializerFeature.WriteMapNullValue,
                // 将String类型的null转成""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将Number类型的null转成0
                SerializerFeature.WriteNullNumberAsZero,
                // 将List类型的null转成[]
                SerializerFeature.WriteNullListAsEmpty,
                // 将Boolean类型的null转成false
                SerializerFeature.WriteNullBooleanAsFalse,
                //全局配置Date的处理方式                
                //SerializerFeature.WriteDateUseDateFormat,
                //跳过Transient
                //SerializerFeature.SkipTransientField,
                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect);

        fastJsonConverter.setFastJsonConfig(config);
        fastJsonConverter.setDefaultCharset(Charset.forName("UTF-8"));
        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        fastJsonConverter.setSupportedMediaTypes(mediaTypeList);
        return fastJsonConverter;
    }
}
