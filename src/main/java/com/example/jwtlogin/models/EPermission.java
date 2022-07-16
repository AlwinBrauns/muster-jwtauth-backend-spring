package com.example.jwtlogin.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write");
    final String permission;
}
