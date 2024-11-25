package com.propilot.performance_management_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.service.UserService;
import com.propilot.performance_management_app.service.UserServiceImpl;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired

private UserService userservice;
	
	
 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody Users updatedUser
    ) {
        try {
            Users updated = userservice.updateUser(id, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
	   
	   
   
   


}

