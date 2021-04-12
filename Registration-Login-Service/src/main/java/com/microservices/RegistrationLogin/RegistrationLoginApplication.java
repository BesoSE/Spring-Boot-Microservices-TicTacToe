package com.microservices.RegistrationLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class RegistrationLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationLoginApplication.class, args);
	}


}
