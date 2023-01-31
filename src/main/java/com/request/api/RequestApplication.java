package com.request.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class RequestApplication {
	public static void main(String[] args) {
		SpringApplication.run(RequestApplication.class, args);

		System.out.println(new BCryptPasswordEncoder().encode("123123"));
	}
}