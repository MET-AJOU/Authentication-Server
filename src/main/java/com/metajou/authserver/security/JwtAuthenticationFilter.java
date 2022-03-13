package com.metajou.authserver.security;

import com.metajou.authserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

        //TODO jwt인증코드
//        if (isAppropriateRequestForFilter(request)) {
//            try {
//                String token = jwtUtil.resolveToken(request);
//                Authentication authentication = jwtUtil.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (JWTVerificationException e) {
//                /* ... */
//            }
//        }

        return chain.filter(exchange);
    }
}
