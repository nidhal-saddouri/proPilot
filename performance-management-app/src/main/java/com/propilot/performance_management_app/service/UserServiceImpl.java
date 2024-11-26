package com.propilot.performance_management_app.service;

import java.util.Optional;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.propilot.performance_management_app.model.Role.RoleName;
import com.propilot.performance_management_app.email.EmailService;
import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	  @Override
	    public List<Users> getApprovedUsers() {
	        return userRepository.findByIsApprovedTrue();
	    }
	  @Override
	  public List<Users> getNotApprovedUsers() {
		    return userRepository.findByIsApprovedFalse();
		}
	  @Override
	  public List<Users> searchUsersByName(String firstName,String lastName) {
	        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName,lastName);
	    }
	  @Override
	  public List<Users> findByNotApprovedandRoleName(String roleName) {
	        try {
	            // Conversion de roleName en enum RoleName
	            RoleName role = RoleName.valueOf(roleName.toUpperCase());
	            return userRepository.findByIsApprovedAndRoleRoleName(false, role);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Role not found: " + roleName, e);
	        }
	    }
	  //eya
	  @Override
	  public Users updateUser(Long id, Users updatedUser) throws Exception {
	        Optional<Users> existingUserOptional = userRepository.findById(id);
	        if (existingUserOptional.isPresent()) {
	            Users existingUser = existingUserOptional.get();
	            existingUser.setFirstName(updatedUser.getFirstName());
	            existingUser.setLastName(updatedUser.getLastName());
	            existingUser.setEmail(updatedUser.getEmail());
	            existingUser.setRole(updatedUser.getRole());
	         

	            return userRepository.save(existingUser);
	        } else {
	            throw new Exception("Utilisateur introuvable avec l'ID : " + id);
	        }}
	 
	  @Override

	  public List<Users> findByApprovedandRoleName(String roleName) {
	        try {
	            // Conversion de roleName en enum RoleName
	            RoleName role = RoleName.valueOf(roleName.toUpperCase());
	            return userRepository.findByIsApprovedAndRoleRoleName(true, role);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Role not found: " + roleName, e);
	        }
	    }
	 
	  @Override
	  public List<Users> findNotApprovedUsers(String firstName, String lastName) {
		    return userRepository.findByIsApprovedAndFirstNameContainingAndLastNameContaining(false, firstName, lastName);
		}
	  @Override
	  public List<Users> findApprovedUsers(String firstName, String lastName) {
		    return userRepository.findByIsApprovedAndFirstNameContainingAndLastNameContaining(true, firstName, lastName);
		}
	  
	//eya
	    
	    @Override
	    public ResponseEntity<?> approveRegistrationUser(Long userId) {
	        try {
	             Users user = userRepository.findById(userId)
	                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId));

	            if (user.isApproved()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur est déjà approuvé.");
	            }
	            user.setApproved(true);
	            userRepository.save(user);
	            emailService.sendValidationEmail(user);

	            return ResponseEntity.ok("L'utilisateur a été approuvé avec succès.");
	        } catch (IllegalArgumentException ex) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	        } catch (Exception ex) {
	            System.err.println("Erreur lors de l'approbation de l'utilisateur : " + ex.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Erreur inattendue lors de l'approbation de l'utilisateur.");
	        }
	    }

	   
	    
	    
	    @Override
		  public Users AddUser(Users user) {
	    	 if (userRepository.existsByEmail(user.getEmail())) {
		            throw new IllegalArgumentException("Email déjà utilisé");
		        }
		        String roleName = user.getRole() != null ? user.getRole().getRoleName().name().toUpperCase() : "EMPLOYEE";
		        Role role = roleRepository.findByRoleName(Role.RoleName.valueOf(roleName));

		       if (role == null) {
		            role = new Role();
		            role.setRoleName(Role.RoleName.valueOf(roleName));
		            role = roleRepository.save(role); 
		        }
		        user.setRole(role);

		        user.setVerified(true);  
		        user.setApproved(false); 
		        Users newUser = userRepository.save(user);
		        return newUser;
		    }
	  
	  
	  
	 

}

