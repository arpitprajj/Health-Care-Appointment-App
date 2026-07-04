package com.hca.api_gateway.util;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter
        extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final RouteValidator routeValidator;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(RouteValidator routeValidator,
                                   JwtUtil jwtUtil) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            if (routeValidator.isSecured.test(exchange.getRequest())) {

                String authHeader = exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader == null || authHeader.isBlank()) {
                    throw new RuntimeException("Missing Authorization Header");
                }

                if (!authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Invalid Authorization Header");
                }

                String token = authHeader.substring(7);

                if (!jwtUtil.validateToken(token)) {
                    throw new RuntimeException("Invalid JWT Token");
                }

                Claims claims = jwtUtil.extractClaims(token);

                String userId = claims.get("userId", String.class);
                String role = claims.get("role", String.class);
                String email = claims.getSubject();

                ServerHttpRequest request = exchange.getRequest()
                        .mutate()
                        .header("X-User-Id", userId)
                        .header("X-Role", role)
                        .header("X-Email", email)
                        .build();

                return chain.filter(
                        exchange.mutate()
                                .request(request)
                                .build()
                );
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}