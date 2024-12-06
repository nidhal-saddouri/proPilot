package com.propilot.performance_management_app;

import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PerformanceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceManagementApplication.class, args);
	}
}
