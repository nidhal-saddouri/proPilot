package com.propilot.performance_management_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propilot.performance_management_app.model.Role.RoleName;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private UserRepository userRepository;

	  @Override
	    public List<Users> getAllUsers() {
	        return userRepository.findAll();
	    }
	  @Override
	  public List<Users> searchUsersByName(String firstName,String lastName) {
	        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName,lastName);
	    }
	// Recherche des utilisateurs par rôle
	  @Override
	    public List<Users> findByRoleName(String roleName) {
	        try {
	            RoleName role = RoleName.valueOf(roleName.toUpperCase());  // Conversion en enum
	            return userRepository.findByRoleRoleName(role);  // Requête basée sur l'enum
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Role not found: " + roleName, e); // Gestion d'erreur si le rôle est invalide
	        }
	    }
	  @Override
	    public List<Users> findByIsApproved(boolean IsApproved){
	    	
	    	return userRepository.findByisApproved(IsApproved);
	    }
}
