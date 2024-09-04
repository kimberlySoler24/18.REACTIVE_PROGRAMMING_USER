package com.todolist.reactive.user.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.context.SecurityContextServerWebExchange;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

//@Component
//public class JwtAuthenticationFilter implements WebFilter {
//
//    @Autowired
//    private JwtUtils jwtUtils;
//    @Autowired
//    private ReactiveUserDetailsService userDetailsService;
////    @Autowired
////    ServerSecurityContextRepository securityContextRepository;
//
//    public JwtAuthenticationFilter(JwtUtils jwtUtils,
//                                   ReactiveUserDetailsService userDetailsService) {
//        this.jwtUtils = jwtUtils;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//
//        if (path.startsWith("/api/auth")) {
//            return chain.filter(exchange);
//        }
//
//        String token = extractToken(request);
//        if (token != null) {
//            return validateToken(token)
//                    .flatMap(email -> userDetailsService.findByUsername(email))
//                    .map(userDetails -> {
//                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//                        return new SecurityContextImpl(auth);
//                    })
//                    .flatMap(securityContext ->
//                            this.serverSecurityContextRepository().save(exchange, securityContext)
//                                    .then(chain.filter(exchange))
//                    )
//                    .switchIfEmpty(chain.filter(exchange));
//        }
//
//        return chain.filter(exchange);
//    }
//
//
//    private String extractToken(ServerHttpRequest request) {
//        String bearerToken = request.getHeaders().getFirst("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    private Mono<String> validateToken(String token) {
//        return jwtUtils.extractUsername(token)
//                .flatMap(email ->
//                        jwtUtils.validateToken(token, email)
//                                .filter(isValid -> isValid)
//                                .map(isValid -> email)
//                )
//                .switchIfEmpty(Mono.empty());
//    }
//
//    @Bean
//    public ServerSecurityContextRepository serverSecurityContextRepository() {
//        return new WebSessionServerSecurityContextRepository();
//    }
//}
