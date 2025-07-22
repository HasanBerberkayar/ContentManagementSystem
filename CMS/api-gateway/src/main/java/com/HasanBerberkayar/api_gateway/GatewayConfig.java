package com.HasanBerberkayar.api_gateway;

import com.HasanBerberkayar.api_gateway.Security.JwtAuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthFilter filter;

    public GatewayConfig(JwtAuthFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))
                .route("content-service", r -> r.path("/content/**", "/cast/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthFilter.Config())))
                        .uri("lb://content-service"))
                .build();
    }
}
