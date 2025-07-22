package com.example.demo.model;

public class User {
    private String username;
    private String secretKey;

    // Constructors
    public User() {}

    public User(String username, String secretKey) {
        this.username = username;
        this.secretKey = secretKey;
    }

    // Getters and Setters
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