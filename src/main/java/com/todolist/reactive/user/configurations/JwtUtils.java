package com.todolist.reactive.user.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Mono<String> generateToken(String username, Map<String, Object> claims) {
        return Mono.fromCallable(() ->
                Jwts.builder()
                        .subject(username)
                        .claims(claims)
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + expiration))
                        .signWith(secretKey)
                        .compact()
        );
    }

    public Mono<String> generateClaims(String username) {
        return Mono.fromCallable(() -> {
            Map<String, Object> claims = new HashMap<>();
            claims.put("rol", "rol");
            return claims;
        }).flatMap(claims -> generateToken(username, claims));
    }

    public Mono<String> extractUsername(String token) {
        return Mono.fromCallable(() -> parseClaims(token).getSubject())
                .doOnNext(subject -> System.out.println(subject));
    }

    public Mono<Boolean> validateToken(String token, String username) {
        return extractUsername(token)
                .map(tokenUsername -> tokenUsername.equals(username) && !isTokenExpired(token));
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }
}
