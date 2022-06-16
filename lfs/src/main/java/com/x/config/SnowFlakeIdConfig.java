package com.x.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.x.commons.util.SnowflakeIdUtil;

@Configuration
public class SnowFlakeIdConfig {

	@Bean
	public SnowflakeIdUtil snowFlakeIdUtil() {
		//TODO:machineId,应该获取Ip地址,通过redis注册Ip地址获取对应的Id的方式
		long dataCenterId = 1L;
		long machineId = 1L;
		return new SnowflakeIdUtil(dataCenterId, machineId);
	}
	
}
