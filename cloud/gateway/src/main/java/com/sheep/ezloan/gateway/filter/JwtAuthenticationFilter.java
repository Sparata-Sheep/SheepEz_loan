package com.sheep.ezloan.gateway.filter;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.sheep.ezloan.support.redis.service.RedisService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Order(0) // 필터 순서를 지정
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter {

    private final WebClient.Builder webClientBuilder;

    private final RedisService redisService;

    @Value("${spring.cloud.gateway.routes[0].uri}")
    private String authUrl;

    @Autowired
    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder, RedisService redisService) {
        this.webClientBuilder = webClientBuilder;
        this.redisService = redisService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // /auth 경로는 필터를 적용하지 않음
        if (path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        // JWT 토큰 추출
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return handleUnauthorized(exchange);
        }

        String token = authHeader.substring(7); // "Bearer " 제거

        // JWT Payload에서 필요한 정보를 추출
        String payload = extractPayload(token);

        // Redis에서 인증 정보 조회
        String redisKey = "auth:" + payload;
        Map<String, Object> cachedClaims = (Map<String, Object>) redisService.getValue(redisKey);

        // Redis 캐시가 있으면 처리
        if (cachedClaims != null) {
            addHeadersToRequest(exchange, cachedClaims);
            return chain.filter(exchange);
        }

        // Auth 서버에서 검증 후 처리
        return validateTokenWithAuthServer(token, redisKey, exchange, chain);
    }

    private void addHeadersToRequest(ServerWebExchange exchange, Map<String, Object> claims) {
        exchange.getRequest()
            .mutate()
            .header("X-User-Id", claims.get("sub").toString())
            .header("X-Role", claims.get("role").toString())
            .header("X-Username", claims.get("username").toString());
    }

    private Mono<Void> validateTokenWithAuthServer(String token, String redisKey, ServerWebExchange exchange,
            GatewayFilterChain chain) {
        return webClientBuilder.build()
            .post()
            .uri(authUrl + "/api/v1/auth/validate-token")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .retrieve()
            .bodyToMono(Map.class)
            .flatMap(responseBody -> handleSuccess(exchange, redisKey, responseBody, chain))
            .onErrorResume(e -> handleUnauthorized(exchange));
    }

    private Mono<Void> handleSuccess(ServerWebExchange exchange, String redisKey, Map<String, Object> responseBody,
            GatewayFilterChain chain) {
        // 유저 정보 Redis에 저장
        redisService.setValueWithExpiry(redisKey, responseBody, 1, TimeUnit.HOURS);

        // 헤더에 유저 정보 추가
        addHeadersToRequest(exchange, responseBody);

        // 체인 필터 처리
        return chain.filter(exchange);
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        log.warn("fail to connect : {}", authUrl);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    // JWT의 Payload 추출 메서드 (Base64 디코딩)
    private String extractPayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token format.");
        }
        return new String(java.util.Base64.getDecoder().decode(parts[1]));
    }

}
