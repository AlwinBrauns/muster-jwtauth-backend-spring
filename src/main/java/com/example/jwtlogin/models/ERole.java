package com.example.jwtlogin.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum ERole {
    ROLE_USER(Set.of(EPermission.USER_READ)),
    ROLE_ADMIN(Set.of(EPermission.USER_READ, EPermission.USER_WRITE));
    private final Set<EPermission> permissions;
}
