package com.propilot.performance_management_app.controller;

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

import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.service.AuthService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private AuthService authService;
	
	
	  // Route pour l'inscription de l'utilisateur
	  @PostMapping("/register")
	    public ResponseEntity<?> signup(@RequestBody Users user) {
	        try {
	            Users newUser = authService.register(user);
	            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	        }
	    }

    // Route pour vérifier l'email de l'utilisateur avec le token
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        try {
            // Log pour confirmer l'entrée dans la méthode
            System.out.println("Vérification de l'email avec token: " + token);
            authService.verifyEmail(token);
            return new ResponseEntity<>("Email vérifié avec succès.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la vérification de l'email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
