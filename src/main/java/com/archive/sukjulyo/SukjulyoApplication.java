package com.archive.sukjulyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SukjulyoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SukjulyoApplication.class, args);
	}

}
