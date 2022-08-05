package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MjcRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(MjcRestApiApplication.class, args);
	}
}
