package com.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

		@Bean
		public CorsWebFilter corsWebFilter() {

			final CorsConfiguration corsConfig = new CorsConfiguration();
			corsConfig.setAllowedOrigins(Collections.singletonList("*"));
			corsConfig.setMaxAge(3600L);
			corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
			corsConfig.addAllowedHeader("*");

			final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", corsConfig);

			return new CorsWebFilter(source);
		}



}
