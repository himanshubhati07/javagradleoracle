package com.example.app.service;

import com.example.app.dto.*;
import com.example.app.entity.AppUser;
import com.example.app.exception.*;
import com.example.app.repository.AppUserRepository;
import com.example.app.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    private final AppUserRepository repository; private final PasswordEncoder encoder; private final JwtUtil jwtUtil;
    public AuthService(AppUserRepository repository, PasswordEncoder encoder, JwtUtil jwtUtil) { this.repository=repository; this.encoder=encoder; this.jwtUtil=jwtUtil; }
    public JwtResponse register(RegisterRequest r) {
        if (repository.existsByEmailIgnoreCase(r.email())) throw new ConflictException("User email already registered");
        AppUser u = new AppUser(); u.setName(r.name().trim()); u.setEmail(r.email().trim().toLowerCase()); u.setPassword(encoder.encode(r.password()));
        AppUser saved = repository.save(u); return new JwtResponse(jwtUtil.generateToken(saved.getEmail()));
    }
    @Transactional(readOnly=true) public JwtResponse login(LoginRequest r) {
        AppUser u = repository.findByEmailIgnoreCase(r.email()).orElseThrow(() -> new BadRequestException("Invalid email or password"));
        if (!encoder.matches(r.password(), u.getPassword())) throw new BadRequestException("Invalid email or password");
        return new JwtResponse(jwtUtil.generateToken(u.getEmail()));
    }
}
