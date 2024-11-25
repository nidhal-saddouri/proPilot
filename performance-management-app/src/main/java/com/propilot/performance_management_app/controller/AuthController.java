package com.propilot.performance_management_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.service.AuthService;
import com.propilot.performance_management_app.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private AuthService authService;
	@Autowired
    private UserService userservice;
	
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
	  @PutMapping("/approve/{userId}")
	  public ResponseEntity<String> approveUser(@PathVariable Long userId) {
	      try {
	          userservice.approveRegistrationUser(userId); // Appel du service pour approuver l'utilisateur
	          return ResponseEntity.ok("User approved successfully.");
	      } catch (Exception e) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error approving user.");
	      }
	  }
	 

   
}
