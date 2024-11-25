package com.propilot.performance_management_app.service;

import org.springframework.http.ResponseEntity;

import com.propilot.performance_management_app.model.Users;

public interface UserService {
	 Users updateUser(Long id, Users updatedUser) throws Exception;
	   ResponseEntity<?> approveRegistrationUser(Long userId);
	   Users register(Users user);

}
