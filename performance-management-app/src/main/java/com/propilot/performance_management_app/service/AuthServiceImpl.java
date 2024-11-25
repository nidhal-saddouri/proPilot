package com.propilot.performance_management_app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService{
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private RoleRepository roleRepository;

	    @Autowired
	    private MailSender mailSender;

	   
	    @Override
	    public Users register(Users user) {
	        // Vérifiez si l'email est déjà utilisé
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
	        sendEmailBeforeApproval(newUser);

	        return newUser;
	    }


	    private void sendEmailBeforeApproval(Users user) {
	        String to = user.getEmail();
	        
	        if (to == null || to.isEmpty()) {
	            throw new IllegalArgumentException("L'adresse e-mail de l'utilisateur est invalide.");
	        }

	        String subject = "Inscription réussie";
	        String text =  "Bonjour " + user.getFirstName() + ",\n\n"
	        		+ "Merci pour votre inscription sur **ProPilot** ! Votre demande est en cours de validation.\n\n"
	        		+ "Nous vous informerons par email dès que votre compte sera approuvé par un administrateur.\n\n"
	        		+ "Merci pour votre patience.\n\n"
	        		+ "Cordialement,\nL'équipe ProPilot"
;
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(text);

	        try {
	            mailSender.send(message);
	            System.out.println("Email de confirmation envoyé à " + to);
	        } catch (Exception e) {
	            System.err.println("Erreur lors de l'envoi de l'e-mail de confirmation : " + e.getMessage());
	            throw new RuntimeException("Impossible d'envoyer l'e-mail de confirmation.");
	        }
	    }
	 
}
