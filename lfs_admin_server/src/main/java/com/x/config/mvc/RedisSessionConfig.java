package com.x.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.x.config.shiro.ShiroRedisSerializer;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class RedisSessionConfig {

	@Bean(name="springSessionDefaultRedisSerializer")
	public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
		//配置使用jackson来进行序列化
		//return new GenericFastJsonRedisSerializer();
		//return new GenericJackson2JsonRedisSerializer();
		return new ShiroRedisSerializer();
	}
	
}
