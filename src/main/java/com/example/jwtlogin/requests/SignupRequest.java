package com.example.jwtlogin.requests;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private Set<String> role;
    private String password;
}
