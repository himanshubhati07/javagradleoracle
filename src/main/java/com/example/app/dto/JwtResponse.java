package com.example.app.dto;

public record JwtResponse(String token, String tokenType, long expiresInSeconds) {
    public JwtResponse(String token) { this(token, "Bearer", 1800); }
}
