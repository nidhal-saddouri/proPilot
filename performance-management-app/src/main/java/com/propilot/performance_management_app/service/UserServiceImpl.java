package com.propilot.performance_management_app.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.UserRepository;

@Service
public class UserServiceImpl{
	 @Autowired
	    private UserRepository userRepository;

	  

	
	    
	 // Méthode pour modifier un utilisateur
	    public Users updateUser(Long id, Users updatedUser) throws Exception {
	        Optional<Users> existingUserOptional = userRepository.findById(id);
	        if (existingUserOptional.isPresent()) {
	            Users existingUser = existingUserOptional.get();
	            existingUser.setFirstName(updatedUser.getFirstName());
	            existingUser.setLastName(updatedUser.getLastName());
	            existingUser.setEmail(updatedUser.getEmail());
	            existingUser.setRole(updatedUser.getRole());
	         

	            return userRepository.save(existingUser);
	        } else {
	            throw new Exception("Utilisateur introuvable avec l'ID : " + id);
	        }
	    }
	    
}