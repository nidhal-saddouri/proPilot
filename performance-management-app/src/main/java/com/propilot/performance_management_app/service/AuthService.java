package com.propilot.performance_management_app.service;

import com.propilot.performance_management_app.model.Users;

public interface AuthService {
	 	Users register(Users user);
	    void verifyEmail(String token);
}
