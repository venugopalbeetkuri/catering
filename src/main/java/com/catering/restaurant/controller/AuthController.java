package com.catering.restaurant.controller;

import com.catering.restaurant.dto.AuthDtos.*;
import com.catering.restaurant.model.AppUser;
import com.catering.restaurant.repo.AppUserRepository;
import com.catering.restaurant.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(AppUserRepository repo, PasswordEncoder encoder, JwtService jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest r) {
        if (repo.existsByUsername(r.username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }
        AppUser u = new AppUser();
        u.setUsername(r.username.toLowerCase());
        u.setPassword(encoder.encode(r.password));
        u.setRoles(Set.of("ROLE_OWNER")); // or ROLE_STAFF based on your flow
        repo.save(u);
        return ResponseEntity.ok(Map.of("status", "registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest r) {
        var user = repo.findByUsername(r.username.toLowerCase()).orElse(null);
        if (user == null || !encoder.matches(r.password, user.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        String token = jwt.generate(user.getUsername(), Map.of("roles", user.getRoles()));
        AuthResponse resp = new AuthResponse();
        resp.token = token;
        resp.username = user.getUsername();
        return ResponseEntity.ok(resp);
    }
}

