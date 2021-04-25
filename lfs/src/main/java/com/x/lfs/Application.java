package com.x.lfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 老徐
 *
 */

@ComponentScan(basePackages= "com.x.lfs.*")
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class Application 
{
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
}
