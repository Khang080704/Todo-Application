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

    public String generateAccessToken(String username, Long user_id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id",  user_id);
        return createToken(claims, username, ACCESS_TOKEN_EXPIRATION);
    }
    public String generateRefreshToken(String username, Long user_id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id",  user_id);
        return createToken(claims, username, REFRESH_TOKEN_EXPIRATION);
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

    public String getAccessTokenFromRefreshToken(String refreshToken) {
        String user_name = extractUsername(refreshToken);
        Long id = extractUserId(refreshToken);
        String newAccessToken = generateAccessToken(user_name, id);
        return newAccessToken;
    }


    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }
    public Long extractUserId(String token) {
        Object id = parseToken(token).get("user_id");
        if (id instanceof Integer i) return i.longValue();
        if (id instanceof Long l) return l;
        return null;
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
    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
