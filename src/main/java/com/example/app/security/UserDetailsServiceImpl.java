package com.example.app.security;

import com.example.app.entity.AppUser;
import com.example.app.repository.AppUserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository repository;
    public UserDetailsServiceImpl(AppUserRepository repository) { this.repository = repository; }
    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser u = repository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(u.getEmail(), u.getPassword(), List.of());
    }
}
