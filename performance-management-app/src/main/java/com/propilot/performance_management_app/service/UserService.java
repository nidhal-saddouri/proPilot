package com.propilot.performance_management_app.service;

import java.util.List;

import com.propilot.performance_management_app.model.Users;

public interface UserService {
	List<Users> getAllUsers();
	 List<Users> searchUsersByName(String firstName,String lastName);
	 List<Users> findByRoleName(String roleName) ;
	 List<Users> findByIsApproved(boolean IsApproved);
	 }
