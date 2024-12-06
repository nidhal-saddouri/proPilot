package com.propilot.performance_management_app.repository;

import java.util.List;
import java.util.Optional;

import com.propilot.performance_management_app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propilot.performance_management_app.model.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);  
    Optional<Users> findByEmail(String email);
    List<Users> findByFirstNameContainingAndLastNameContaining(String firstName,String lastName);
    List<Users> findByIsApprovedAndRoleRoleName(boolean isApproved, String roleName);
    List<Users> findByIsApprovedTrue();
    List<Users> findByIsApprovedFalse();
    List<Users> findByIsApprovedAndFirstNameContainingAndLastNameContaining( boolean isApproved, String firstName, String lastName);

}
