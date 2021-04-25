package com.x.lfs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class OrikaConfig {
	
	@Bean
	public MapperFacade orikaMapper() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper;
	}
	
	
}
