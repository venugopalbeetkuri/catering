package com.catering.restaurant.security;

import com.catering.restaurant.repo.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("prod")
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(AppUserRepository repo) {
        return username -> repo.findByUsername(username)
                .map(u -> User.withUsername(u.getUsername())
                        .password(u.getPassword())
                        .authorities(u.getRoles().toArray(String[]::new))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService uds, BCryptPasswordEncoder enc) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(enc);
        return p;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtService jwtService,
                                           AppUserRepository userRepo,
                                           DaoAuthenticationProvider provider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(provider)
                .addFilterBefore(new JwtAuthFilter(jwtService, userRepo), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                "/h2/**"
                        ).permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .headers(h -> h.frameOptions(f -> f.disable())); // for H2 console
        return http.build();
    }
}
