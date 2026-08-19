package com.example.app.controller;

import com.example.app.dto.*;
import com.example.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name="Authentication")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service=service; }
    @PostMapping("/register") @Operation(summary="Register a user and issue a JWT")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request)); }
    @PostMapping("/login") @Operation(summary="Log in and issue a JWT")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) { return ResponseEntity.ok(service.login(request)); }
}
