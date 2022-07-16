package com.example.jwtlogin.repositories;

import com.example.jwtlogin.models.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Benutzer, Long> {
    Optional<Benutzer> findByUsername(String username);
    boolean existsByUsername(String username);
}
