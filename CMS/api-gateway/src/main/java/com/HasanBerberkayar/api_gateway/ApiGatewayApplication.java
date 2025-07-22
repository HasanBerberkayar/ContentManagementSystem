package com.HasanBerberkayar.api_gateway;

import com.HasanBerberkayar.api_gateway.Security.JwtAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtAuthFilter jwtAuthFilter) {
		return builder.routes()
				.route("auth_route", r -> r.path("/auth/**")
						.filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config())))
						.uri("lb://auth-service"))
				.route("user_route", r -> r.path("/users/**")
						.filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config())))
						.uri("lb://user-service"))
				.route("product_route", r -> r.path("/products/**")
						.filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config())))
						.uri("lb://product-service"))
				.route("order_route", r -> r.path("/orders/**")
						.filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config())))
						.uri("lb://order-service"))
				.route("content_route", r -> r.path("/content/**")
						.filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config())))
						.uri("lb://content-service"))
				.build();
	}

	@Bean
	public CommandLineRunner testBeanPresence(JwtAuthFilter filter) {
		return args -> System.out.println("JwtAuthFilter bean is present: " + filter);
	}
}
