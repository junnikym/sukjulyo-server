package com.archive.sukjulyo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${frontend-app.entry}")
	private String frontUrl;

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/templates/",
			"classpath:/static/"
	};

	private final long MAX_AGE_SECS = 3600;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/**")
				.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
				.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(frontUrl)
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(MAX_AGE_SECS);
	}
}