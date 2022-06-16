package com.x;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 老徐
 *
 */
@SpringBootApplication
@ComponentScan(basePackages= "com.x.*")
@EnableTransactionManagement  //配置事务管理
//@EnableOpenApi  //配置swagger
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
