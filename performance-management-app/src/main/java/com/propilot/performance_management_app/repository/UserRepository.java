package com.propilot.performance_management_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propilot.performance_management_app.model.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);  // Vérifier si l'email existe déjà
    Optional<Users> findByVerificationToken(String verificationToken);

}
