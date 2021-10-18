package com.archive.sukjulyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:database.properties" })
public class SukjulyoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SukjulyoApplication.class, args);
	}

}
