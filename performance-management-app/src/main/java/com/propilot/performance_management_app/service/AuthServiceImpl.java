package com.propilot.performance_management_app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import com.propilot.performance_management_app.email.EmailService;
import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.UserRepository;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService{
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private RoleRepository roleRepository;

	    
	    @Autowired
		private EmailService emailService;
	    @Autowired
	    private MailSender mailSender;

	   
	    @Override
	    public Users register(Users user) {
	        
	        if (userRepository.existsByEmail(user.getEmail())) {
	            throw new IllegalArgumentException("Email déjà utilisé");
	        }

	        // Obtenez le nom du rôle demandé ou définissez un rôle par défaut
	        String roleName = user.getRole() != null ? user.getRole().getRoleName().name().toUpperCase() : "EMPLOYEE";

	        // Cherchez le rôle dans la base de données
	        Role role = roleRepository.findByRoleName(Role.RoleName.valueOf(roleName));

	        // Si le rôle n'existe pas, créez-le automatiquement
	        if (role == null) {
	            role = new Role();
	            role.setRoleName(Role.RoleName.valueOf(roleName));
	            role = roleRepository.save(role); // Sauvegardez le nouveau rôle dans la base
	        }

	        // Associez le rôle à l'utilisateur
	        user.setRole(role);

	        // Configurez les autres propriétés par défaut
	        user.setVerified(true);  // Par exemple, marquer comme vérifié par défaut
	        user.setApproved(false); // Par défaut, l'utilisateur n'est pas approuvé

	        // Sauvegardez l'utilisateur dans la base
	        Users newUser = userRepository.save(user);

	        // Envoyez un email avant approbation
	        try {
	            emailService.sendRegistrationEmail(newUser);
	        } catch (MessagingException e) {
	            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
	        
	        }
 
	        

	        return newUser;
	    }


	    

	   
	 
}
