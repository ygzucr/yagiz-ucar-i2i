package com.example.spring_boot_bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("com.example.spring_boot_bootstrap.repository")
@EntityScan("com.example.spring_boot_bootstrap.model")
public class SpringBootBootstrapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBootstrapApplication.class, args);
	}
}
