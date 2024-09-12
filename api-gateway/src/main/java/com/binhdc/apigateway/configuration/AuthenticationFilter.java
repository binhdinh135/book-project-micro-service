package com.binhdc.apigateway.configuration;

import com.binhdc.apigateway.dto.ApiResponse;
import com.binhdc.apigateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

    IdentityService identityService;
    ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication filter....");

        // Get token from authorization header
        // Lay token tu authorization header.
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            // Neu list khong co phan tu nao -> return ra loi khong the authentication.
            return unauthenticated(exchange.getResponse());

        // boi vi token co chua Bearer + token. Chung ta chi can authenticate voi token
        // nen se loai bo String Bearer o phia dau.
        String token = authHeader.get(0).replace("Bearer ", "");
        log.info("Token: {}", token);

        // goi toi Identity service de co the introspect duoc (verify) token nay.
        return identityService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getResult().isValid())
                // Neu token valid -> pass qua buoc verify token -> chuyen toi cac filter tiep theo
                return chain.filter(exchange);
            else
                // Neu token not valid -> ngay lap tuc chan tai day -> Throw exception cho client.
                return unauthenticated(exchange.getResponse());
        }).onErrorResume(
                // Tat ca cac case throwable -> loi -> deu se khong authenticated dc -> Throw exception cho client.
                throwable -> unauthenticated(exchange.getResponse())
        );
    }

    @Override
    public int getOrder() {
        // Set -1 de dam bao rang filter cua chung ta co do uu tien cao
        // truoc khi chay vao cac filter khac trong API Gateway filters.
        return -1;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Unauthenticated")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

}
