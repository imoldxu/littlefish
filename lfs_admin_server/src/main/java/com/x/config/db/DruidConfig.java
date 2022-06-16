package com.x.config.db;

//import javax.sql.DataSource;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * 配置druid数据库连接池，支持多种sql数据库
 * @author 老徐
 *
 */
@Configuration
public class DruidConfig {

//	/**
//	 * 多数据源配置Druid的数据源，单数据源可以通过配置文件自动配置
//	 * @return
//	 */
//	@Primary
//	@Bean
//	@ConfigurationProperties("spring.datasource.druid.one")
//	public DataSource dataSourceOne(){
//	    return DruidDataSourceBuilder.create().build();
//	}
//	@Bean
//	@ConfigurationProperties("spring.datasource.druid.two")
//	public DataSource dataSourceTwo(){
//	    return DruidDataSourceBuilder.create().build();
//	}
	
}
