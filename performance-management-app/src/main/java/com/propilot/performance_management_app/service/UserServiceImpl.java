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
	    public List<Users> getApprovedUsers() {
	        return userRepository.findByIsApprovedTrue();
	    }
	  @Override
	  public List<Users> getNotApprovedUsers() {
		    return userRepository.findByIsApprovedFalse();
		}
	  @Override
	  public List<Users> searchUsersByName(String firstName,String lastName) {
	        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName,lastName);
	    }
	// Recherche des utilisateurs par rôle
	  @Override

	  public List<Users> findByNotApprovedandRoleName(String roleName) {
	        try {
	            // Conversion de roleName en enum RoleName
	            RoleName role = RoleName.valueOf(roleName.toUpperCase());

	            // Appeler le repository avec le rôle et le statut "non approuvé"
	            return userRepository.findByIsApprovedAndRoleRoleName(false, role);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Role not found: " + roleName, e);
	        }
	    }
	 
	  @Override

	  public List<Users> findByApprovedandRoleName(String roleName) {
	        try {
	            // Conversion de roleName en enum RoleName
	            RoleName role = RoleName.valueOf(roleName.toUpperCase());

	            // Appeler le repository avec le rôle et le statut "non approuvé"
	            return userRepository.findByIsApprovedAndRoleRoleName(true, role);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Role not found: " + roleName, e);
	        }
	    }
	 
	  @Override
	  public List<Users> findNotApprovedUsers(String firstName, String lastName) {
		    // Utilisation de la méthode repository pour filtrer par prénom et nom
		    return userRepository.findByIsApprovedAndFirstNameContainingAndLastNameContaining(false, firstName, lastName);
		}
	  @Override
	  public List<Users> findApprovedUsers(String firstName, String lastName) {
		    // Utilisation de la méthode repository pour filtrer par prénom et nom
		    return userRepository.findByIsApprovedAndFirstNameContainingAndLastNameContaining(true, firstName, lastName);
		}
}
