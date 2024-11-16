package com.example.ProPilot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProPilot.model.Users;
import com.example.ProPilot.service.UserService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserService userService;
	
	
	  // Route pour l'inscription de l'utilisateur
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
    	  System.out.println("Request received at /register endpoint."); 
        try {
            // Log pour confirmer l'entrée dans la méthode
            System.out.println("Inscription de l'utilisateur en cours...");
            // Appel de la méthode register pour inscrire l'utilisateur
            Users newUser = userService.register(user);
            return new ResponseEntity<>("Utilisateur inscrit avec succès. Un email de confirmation a été envoyé.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de l'inscription de l'utilisateur.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Route pour vérifier l'email de l'utilisateur avec le token
    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        try {
            // Log pour confirmer l'entrée dans la méthode
            System.out.println("Vérification de l'email avec token: " + token);
            userService.verifyEmail(token);
            return new ResponseEntity<>("Email vérifié avec succès.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la vérification de l'email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
