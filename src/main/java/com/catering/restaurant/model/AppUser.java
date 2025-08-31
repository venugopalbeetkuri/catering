package com.catering.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // BCrypt

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles; // e.g., ROLE_OWNER, ROLE_STAFF

}
