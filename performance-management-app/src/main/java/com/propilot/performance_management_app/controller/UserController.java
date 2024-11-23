package com.propilot.performance_management_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.service.UserService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private UserService userservice;
	 @GetMapping
	    public List<Users> getUsers() {
	        return userservice.getAllUsers();
	        
	    }
	 @GetMapping("/search")
	    public List<Users> searchUsers(@RequestParam(name = "firstname") String firstname,@RequestParam(name = "lastname") String lastname) {
	        return userservice.searchUsersByName(firstname,lastname);
	    }
	   @GetMapping("/searchRole")
	    public List<Users> searchByRole(@RequestParam("roleName") String roleName) {
	        return userservice.findByRoleName(roleName);
	    }
	   @GetMapping("/searchApproved")
	   public List<Users> searchApproved(@RequestParam(name="isapproved") boolean IsApproved){
		   return userservice.findByIsApproved(IsApproved);
	   }
	 
}
