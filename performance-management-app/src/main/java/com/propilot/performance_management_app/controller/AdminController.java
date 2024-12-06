package com.propilot.performance_management_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propilot.performance_management_app.model.admin;
import com.propilot.performance_management_app.service.Adminservice;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("admin")
public class AdminController {
	  @Autowired
	    private Adminservice adminService;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
	        String email = credentials.get("email");
	        String password = credentials.get("password");

	        admin adminUser = adminService.login(email, password);

	        if (adminUser != null) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("admin", adminUser);

	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
	    }

}
