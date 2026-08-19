package com.example.app.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(@NotBlank @Size(max=100) String name,
    @NotBlank @Email @Size(max=160) String email,
    @NotBlank @Size(min=8,max=100) String password) {}
