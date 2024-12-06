package com.propilot.performance_management_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propilot.performance_management_app.model.admin;
import com.propilot.performance_management_app.repository.AdminRepository;



@Service
public class Adminservice {
	@Autowired
    private AdminRepository adminRepository;

	public admin login(String email, String password) {
	    System.out.println("Tentative de login pour: " + email);  
	    admin adminUser = adminRepository.findByEmail(email);
	    System.out.println("Admin trouvé: " + adminUser);
	    
	    if (adminUser != null && adminUser.getPassword().equals(password)) {
	        System.out.println("Authentification réussie pour: " + email);
	        return adminUser;
	    } else {
	        System.out.println("Échec de l'authentification pour: " + email);
	        return null;
	    }
	}

}
