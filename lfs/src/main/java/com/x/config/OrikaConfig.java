package com.x.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * 配置对象转换，实现po、vo、bo、dto的互相转换
 * @author 老徐
 *
 */
@Configuration
public class OrikaConfig {

	/**
	 * 通用的mapper，性能低于 BoundMapperFacade，BoundMapperFacade需要事前建立对象与对象的装换
	 * @return
	 */
	@Bean
	public MapperFacade orikaMapper() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

//
//		mapperFactory.classMap(User.class, PersonDestination.class)
//		   .field("firstName", "givenName")
//		   .field("lastName", "sirName")
//		   .byDefault()
//		   .register();
		
//		BoundMapperFacade<A, B> mapper = mapperFactory.getMapperFacade(A, B);
		
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper;
	}
	
	
}
