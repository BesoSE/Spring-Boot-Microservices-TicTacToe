package com.microservices.Tictactoe.dto;

public class LoginResponse {
    private String jwt;
    private String username;

    public LoginResponse() {
    }

    public LoginResponse(String jwt, String username) {
        this.jwt = jwt;
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
