package com.swipeme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwipemeServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwipemeServicesApplication.class, args);
	}
}