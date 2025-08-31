package com.catering.restaurant.security;

import com.catering.restaurant.repo.AppUserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AppUserRepository userRepo;

    public JwtAuthFilter(JwtService jwtService, AppUserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtService.isValid(token)) {
                String username = jwtService.getUsername(token);
                var user = userRepo.findByUsername(username).orElse(null);
                if (user != null) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        chain.doFilter(req, res);
    }
}
