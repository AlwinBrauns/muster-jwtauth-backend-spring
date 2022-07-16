package com.example.jwtlogin.controllers;

import com.example.jwtlogin.models.Benutzer;
import com.example.jwtlogin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers(){
        List<Benutzer> allUser = userRepository.findAll();
        return ResponseEntity.ok(allUser);
    }
}
