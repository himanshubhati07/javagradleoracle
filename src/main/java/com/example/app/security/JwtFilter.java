package com.example.app.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    public JwtFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil; this.userDetailsService = userDetailsService;
    }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtUtil.isValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails details = userDetailsService.loadUserByUsername(jwtUtil.extractSubject(token));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities()));
            }
        }
        chain.doFilter(request, response);
    }
}
