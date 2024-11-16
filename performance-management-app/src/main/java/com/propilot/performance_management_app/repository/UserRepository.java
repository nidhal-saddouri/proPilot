package com.example.ProPilot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProPilot.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);  // Vérifier si l'email existe déjà
    Optional<Users> findByVerificationToken(String verificationToken);

}