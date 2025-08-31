package com.catering.restaurant.dto;

public class AuthDtos {
    public static class LoginRequest {
        public String username;
        public String password;
    }
    public static class RegisterRequest {
        public String username;
        public String password;
    }
    public static class AuthResponse {
        public String token;
        public String username;
    }
}
