package com.x.lfs.config.mvc;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

	@Value("${x.basePackage}")
	private String basePackage;

	@Value("#{ @environment['swagger.enable'] ?: true }")
	private boolean swaggerEnable;

	@Value("${server.port}")
	private String serverPort;

	private String baseUrl = "";

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				// .pathMapping("/")
				.enable(swaggerEnable)
				// .host("http://127.0.0.1:"+serverPort)
				.groupName("x").select() // 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.basePackage(basePackage + ".controller")).paths(PathSelectors.any()) // 对所有路径进行监控
				.build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("x系统").description("x系统demo").version("1.0.0").build();
		return apiInfo;
	}

	public void addInterceptors(InterceptorRegistry registry) {
		try {
			Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
			List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils
					.getField(registrationsField, registry);
			if (registrations != null) {
				for (InterceptorRegistration interceptorRegistration : registrations) {
					interceptorRegistration.excludePathPatterns("/swagger**/**").excludePathPatterns("/webjars/**")
							.excludePathPatterns("/v3/**").excludePathPatterns("/doc.html");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
		registry.addResourceHandler(baseUrl + "/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
				.resourceChain(false);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(baseUrl + "/swagger-ui/")
				.setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/pet").allowedOrigins("http://editor.swagger.io");
		registry.addMapping("/v2/api-docs.*").allowedOrigins("http://editor.swagger.io");
	}
}
