package com.metajou.authserver.security;

import com.metajou.authserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        //TODO
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
