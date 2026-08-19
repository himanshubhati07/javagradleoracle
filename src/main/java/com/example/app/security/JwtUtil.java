package com.example.app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key;
    private final long expirationMs;
    public JwtUtil(@Value("${jwt.secret:office-attendance-production-safe-default-secret-key-2025}") String secret,
                   @Value("${jwt.expiration-ms:1800000}") long expirationMs) {
        String safe = (secret == null || secret.length() < 32) ? "office-attendance-production-safe-default-secret-key-2025" : secret;
        this.key = Keys.hmacShaKeyFor(safe.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }
    public String generateToken(String subject) {
        Date now = new Date();
        return Jwts.builder().subject(subject).issuedAt(now).expiration(new Date(now.getTime() + expirationMs))
            .signWith(key, Jwts.SIG.HS256).compact();
    }
    public String extractSubject(String token) { return parse(token).getPayload().getSubject(); }
    public boolean isValid(String token) {
        try { return parse(token).getPayload().getExpiration().after(new Date()); }
        catch (JwtException | IllegalArgumentException ex) { return false; }
    }
    private Jws<Claims> parse(String token) { return Jwts.parser().verifyWith(key).build().parseSignedClaims(token); }
}
