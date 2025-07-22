package com.example.demo.model;

public class User {
    private String username;
    private String secretKey; // Khóa bí mật cho 2FA

    // Constructor, getters, setters
    public User(String username, String secretKey) {
        this.username = username;
        this.secretKey = secretKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}