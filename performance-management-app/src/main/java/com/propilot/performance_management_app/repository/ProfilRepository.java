package com.propilot.performance_management_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propilot.performance_management_app.model.Profil;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
}
