package com.example.jwtlogin.controllers;

import com.example.jwtlogin.models.ERole;
import com.example.jwtlogin.models.Role;
import com.example.jwtlogin.models.Benutzer;
import com.example.jwtlogin.repositories.RoleRepository;
import com.example.jwtlogin.repositories.UserRepository;
import com.example.jwtlogin.requests.LoginRequest;
import com.example.jwtlogin.requests.SignupRequest;
import com.example.jwtlogin.responses.JwtResponse;
import com.example.jwtlogin.security.userdetails.MyUserDetailsImpl;
import com.example.jwtlogin.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    final private AuthenticationManager authenticationManager;
    final private PasswordEncoder passwordEncoder;
    final private JwtUtils jwtUtils;
    final private RoleRepository roleRepository;
    final private UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        MyUserDetailsImpl userDetails = (MyUserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                JwtResponse.builder()
                        .token(jwt)
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .roles(roles)
                .build()
        );
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        Benutzer user = Benutzer.builder().username(signUpRequest.getUsername()).password(passwordEncoder.encode(signUpRequest.getPassword())).build();
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role->{
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }

            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
