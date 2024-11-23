package com.propilot.performance_management_app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.UserRepository;

@Service
public class UserService {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private RoleRepository roleRepository;

	    @Autowired
	    private MailSender mailSender;

	    // Méthode d'enregistrement
	    public Users register(Users user) {
	        // Vérifier si l'email existe déjà
	        if (userRepository.existsByEmail(user.getEmail())) {
	            throw new IllegalArgumentException("Email déjà utilisé");
	        }

	        // Assume user.getRole() is an instance of the Role enum
	        String roleName = user.getRole() != null ? user.getRole().getRoleName().name().toUpperCase() : "EMPLOYEE";
	        Role role = roleRepository.findByRoleName(Role.RoleName.valueOf(roleName));
	        if (role == null) {
	            throw new IllegalArgumentException("Rôle invalide. Choisissez entre 'EMPLOYEE' ou 'MANAGER'");
	        }
	        user.setRole(role);
	        user.setVerified(true); 
	        // Créer un utilisateur avec approbation false par défaut
	        user.setApproved(false); // L'utilisateur est en attente d'approbation
	        user.setVerificationToken(UUID.randomUUID().toString()); // Token de vérification

	        // Sauvegarder l'utilisateur dans la base de données
	        Users newUser = userRepository.save(user);

	        // Envoyer un email pour informer l'utilisateur
	        sendConfirmationEmail(newUser);

	        return newUser;
	    }

	    // Méthode pour envoyer un email de confirmation
	    private void sendConfirmationEmail(Users user) {
	        String to = user.getEmail();
	        
	        // Vérifier si l'e-mail est valide
	        if (to == null || to.isEmpty()) {
	            throw new IllegalArgumentException("L'adresse e-mail de l'utilisateur est invalide.");
	        }

	        String subject = "Inscription réussie";
	        String text =  "Bonjour " + user.getFirstName() + ",\n\n"
	                + "Merci de vous être inscrit sur **ProPilot** ! 🎉 Nous avons bien reçu votre demande d'inscription. "
	                + "Cependant, avant de pouvoir accéder à toutes les fonctionnalités de l'application, nous devons d'abord approuver votre compte.\n\n"
	                + "Que se passe-t-il ensuite ?\n\n"
	                + "- Vous devez attendre l'approbation de l'administrateur, qui vérifiera les informations de votre compte.\n"
	                + "- Dès que votre compte est approuvé, vous recevrez un autre email confirmant votre accès à l'application.\n\n"
	                + "Nous vous remercions pour votre patience et restons à votre disposition pour toute question. "
	                + "N'hésitez pas à nous contacter si vous avez besoin de plus d'informations.\n\n"
	                + "Cordialement,\nL'équipe de ProPilot";
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(text);

	        try {
	            mailSender.send(message);
	            // Log pour confirmer l'envoi
	            System.out.println("Email de confirmation envoyé à " + to);
	        } catch (Exception e) {
	            // Log l'erreur pour le diagnostic
	            System.err.println("Erreur lors de l'envoi de l'e-mail de confirmation : " + e.getMessage());
	            throw new RuntimeException("Impossible d'envoyer l'e-mail de confirmation.");
	        }
	    }

	    // Méthode de vérification de l'email avec le token
	    public void verifyEmail(String token) {
	        Optional<Users> userOptional = userRepository.findByVerificationToken(token);
	        if (userOptional.isPresent()) {
	            Users user = userOptional.get();
	            user.setApproved(true);  // Marquer l'utilisateur comme approuvé
	            user.setVerificationToken(null); // Supprimer le token après vérification
	            userRepository.save(user);
	        } else {
	            throw new IllegalArgumentException("Token de vérification invalide ou expiré.");
	        }
	    }

		}