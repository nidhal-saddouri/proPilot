package com.propilot.performance_management_app.controller;

import java.util.List;

import com.propilot.performance_management_app.DTO.SignupRequest;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.service.AuthenticationRequest;
import com.propilot.performance_management_app.service.AuthenticationResponse;
import com.propilot.performance_management_app.service.UserServiceImpl;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
    private UserServiceImpl userservice;
	
	
	  
	 @GetMapping("users-approved")
	    public List<Users> getUsers() {
	        return userservice.getApprovedUsers();
	        
	    }

	 @GetMapping("/users-not-approved")
	    public List<Users> getNotApprovedUsers() {
	        return userservice.getNotApprovedUsers(); // Méthode pour récupérer les utilisateurs non approuvés
	    }

	 @GetMapping("/search")
	    public List<Users> searchUsers(@RequestParam(name = "firstname") String firstname,@RequestParam(name = "lastname") String lastname) {
	        return userservice.searchUsersByName(firstname,lastname);
	    }

	   @GetMapping("/searchRoleNotApproved")
	    public List<Users> searchNotApprovedByRole(@RequestParam("roleName") String roleName) {
	        return userservice.findByNotApprovedandRoleName(roleName);
	    }

	   @GetMapping("/searchRoleApproved")
	    public List<Users> searchApprovedByRole(@RequestParam("roleName") String roleName) {
	        return userservice.findByApprovedandRoleName(roleName);
	    }

	   @GetMapping("/searchNotApproved")
	   public List<Users> searchNotApproved(
	       @RequestParam(name="firstName", required=false, defaultValue="") String firstName,
	       @RequestParam(name="lastName", required=false, defaultValue="") String lastName) {
	       return userservice.findNotApprovedUsers(firstName, lastName);
	   }

	   @GetMapping("/searchApprovedUsers")
	   public List<Users> searchApprovedUser(
	       @RequestParam(name="firstName", required=false, defaultValue="") String firstName,
	       @RequestParam(name="lastName", required=false, defaultValue="") String lastName) {
	       return userservice.findApprovedUsers(firstName, lastName);
	   }

		 @PostMapping("/AddUser")
		 public ResponseEntity<?> signup(@RequestBody SignupRequest user) {
	          System.out.println(user.getRole());
		        try {
		            Users newUser = userservice.AddUser(user);
		            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
		        } catch (IllegalArgumentException e) {
		            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		        }
		    }
		 @PutMapping("/update/{id}")
		    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
		        try {
		            Users user = userservice.updateUser(id, updatedUser);
		            return ResponseEntity.ok(user);
		        } catch (Exception e) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur : " + e.getMessage());
		        }
		    }
		 
		 
//		    @PostMapping("/auth")
//		    public ResponseEntity<AuthenticationResponse> authenticate(
//		            @RequestBody @Valid AuthenticationRequest authenticationRequest
//		    ){
//		        return ResponseEntity.ok(userservice.authenticate(authenticationRequest));
//		    }

	   
	   
   
   


}

