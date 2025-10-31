package com.example.todoapplication.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final String Secret = "your-very-secret-key-that-should-be-long-enough";

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;  // 15 phút
    private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 ngày

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Secret.getBytes());
    }

    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, ACCESS_TOKEN_EXPIRATION);
    }
    public String generateRefreshToken(String username) {
        return createToken(new HashMap<>(), username, REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
