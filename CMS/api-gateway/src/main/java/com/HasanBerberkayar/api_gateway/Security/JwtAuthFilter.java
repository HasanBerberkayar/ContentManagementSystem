package com.HasanBerberkayar.api_gateway.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    private JwtService jwtService;

    public JwtAuthFilter() {
        super(Config.class);
        System.out.println("[JwtAuthFilter] Constructor called");
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, int status, String message) {
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.valueOf(status));
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"error\": \"" + message + "\"}";
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            System.out.println("[JwtAuthFilter] Path: " + path);

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            System.out.println("[JwtAuthFilter] Authorization header: " + authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("[JwtAuthFilter] Missing or invalid Authorization header");
                return writeErrorResponse(exchange, 401, "Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);
            System.out.println("[JwtAuthFilter] Token: " + token);

            if (!jwtService.validateToken(token)) {
                System.out.println("[JwtAuthFilter] Token validation failed");
                return writeErrorResponse(exchange, 401, "Token validation failed");
            }

            String username = jwtService.extractUsername(token);
            System.out.println("[JwtAuthFilter] Token valid, username: " + username);

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties for the filter can be defined here
    }
}