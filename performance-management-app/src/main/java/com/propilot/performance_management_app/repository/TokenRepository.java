package com.propilot.performance_management_app.repository;

import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String token);
}
