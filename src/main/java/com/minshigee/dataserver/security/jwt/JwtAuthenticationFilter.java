package com.minshigee.dataserver.security.jwt;

import com.minshigee.dataserver.exception.ErrorCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authorization.ServerWebExchangeDelegatingServerAccessDeniedHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/*
    Component로 추가하고 security_chain에 addfilter하면 2번 호출되는 문제가 발생함.
    따라서 SecurityConfig에 JwtUtil을 Application Context에서 가져와 new JwtAuthenticationFilter(jwtUtil)방식으로
    사용함.
 */
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtils jwtUtils = JwtUtils.getInstance();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (jwtUtils.isAppropriateRequestForFilter(request)) {
            try {
                String token = jwtUtils.resolveToken(request);
                Authentication authentication = jwtUtils.getAuthentication(token);
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            } catch (Exception e) {
            }
        }

        return Mono.error(ErrorCode.AUTH_TOKEN_ERROR.build());
    }

}