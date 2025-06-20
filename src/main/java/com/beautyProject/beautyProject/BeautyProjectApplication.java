package com.beautyProject.beautyProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.beautyProject.beautyProject.repository")
//@EntityScan(basePackages = "com.beautyProject.beautyProject.model.entity")
public class BeautyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeautyProjectApplication.class, args);
	}
}