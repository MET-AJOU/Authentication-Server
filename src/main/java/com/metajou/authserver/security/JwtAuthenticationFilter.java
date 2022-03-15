package com.metajou.authserver.security;

import com.metajou.authserver.util.JwtUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
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

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (jwtUtil.isAppropriateRequestForFilter(request)) {
            try {
                String token = jwtUtil.resolveToken(request);
                Authentication authentication = jwtUtil.getAuthentication(token);
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                        //.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return chain.filter(exchange);
    }



}
