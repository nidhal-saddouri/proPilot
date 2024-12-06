package com.propilot.performance_management_app.service;


import com.propilot.performance_management_app.DTO.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.propilot.performance_management_app.email.EmailService;
import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.UserRepository;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private RoleRepository roleRepository;

	    
	    @Autowired
		private EmailService emailService;

		@Autowired
		private PasswordEncoder passwordEncoder;


	    public Users register(SignupRequest signupRequest) {

	        if (userRepository.existsByEmail(signupRequest.getEmail())) {
	            throw new IllegalArgumentException("Email déjà utilisé");
	        }
	        Role role = roleRepository.findByRoleName(signupRequest.getRole());
			Users user = new Users();
	        user.setRole(role);
			user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			user.setFirstName(signupRequest.getFirstName());
			user.setLastName(signupRequest.getLastName());
			user.setEmail(signupRequest.getEmail());
	        user.setVerified(true);
	        user.setApproved(false);
	        Users newUser = userRepository.save(user);
	        try {emailService.sendRegistrationEmail(newUser);
        } catch (MessagingException e) {
	            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());

        }
	        return newUser;
	    }


	    

	   
	 
}
