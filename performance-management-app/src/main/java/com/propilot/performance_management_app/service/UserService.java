package com.propilot.performance_management_app.service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.propilot.performance_management_app.model.Users;

public interface UserService {
	 Users updateUser(Long id, Users updatedUser)throws Exception;
	   ResponseEntity<?> approveRegistrationUser(Long userId);
	List<Users> getApprovedUsers();
	 List<Users> searchUsersByName(String firstName,String lastName);
	 List<Users> getNotApprovedUsers();
	 List<Users> findNotApprovedUsers(String firstName, String lastName);
	 List<Users> findApprovedUsers(String firstName, String lastName) ;
	 List<Users> findByNotApprovedandRoleName(String roleName);
	 List<Users> findByApprovedandRoleName(String roleName);
	 Users AddUser(Users users);
	Users updateProfil(Long id, Users userDetails);
	 
	 }

		


