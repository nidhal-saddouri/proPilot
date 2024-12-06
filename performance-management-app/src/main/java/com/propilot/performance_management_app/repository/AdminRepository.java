package com.propilot.performance_management_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.propilot.performance_management_app.model.admin;


public interface AdminRepository extends JpaRepository<admin, Integer>{
	     admin findByEmail(String email);

}
