package com.microservices.apigateway.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;



@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p->p.path("/api/registration/**")
                        .uri("lb://registration-login"))
                .route(p->p.path("/api/logout/**")
                        .uri("lb://registration-login"))
                .route(p->p.path("/api/login/**")
                        .uri("lb://tic-tac-toe"))
                .route(p->p.path("/api/start/**")
                        .uri("lb://tic-tac-toe"))
                .route(p->p.path("/api/connect/**")
                        .uri("lb://tic-tac-toe"))
                .route(p->p.path("/api/connect/random/**")
                        .uri("lb://tic-tac-toe"))
                .route(p->p.path("/api/gameplay/**")
                        .uri("lb://tic-tac-toe"))
                .route(p->p.path("/api/finish/**")
                        .uri("lb://tic-tac-toe"))
                .route(p->p.path("/gameplay/**")
                        .uri("lb://tic-tac-toe"))
                .build();
    }



}
