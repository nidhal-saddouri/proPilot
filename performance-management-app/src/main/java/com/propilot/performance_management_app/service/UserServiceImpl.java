package com.propilot.performance_management_app.service;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.propilot.performance_management_app.security.jwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.propilot.performance_management_app.model.Role;
import com.propilot.performance_management_app.model.Token;
import com.propilot.performance_management_app.DTO.SignupRequest;
import com.propilot.performance_management_app.email.EmailService;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.RoleRepository;
import com.propilot.performance_management_app.repository.TokenRepository;
import com.propilot.performance_management_app.repository.UserRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor

public class UserServiceImpl {
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bcrybt;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private jwtService jwtService;

	@Autowired
	private TokenRepository tokenRepository;
	
	//manel

	    public List<Users> getApprovedUsers() {
	        return userRepository.findByIsApprovedTrue();
	    }

	  public List<Users> getNotApprovedUsers() {
		    return userRepository.findByIsApprovedFalse();
		}
	  public List<Users> searchUsersByName(String firstName,String lastName) {
	        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName,lastName);
	    }
	  public List<Users> findByNotApprovedandRoleName(String roleName) {
	        try {
	            // Conversion de roleName en enum RoleName
	            return userRepository.findByIsApprovedAndRoleRoleName(false, roleName);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Role not found: " + roleName, e);
	        }
	    }
	  //eya
	  public Users updateUser(Long id, Users updatedUser) throws Exception {
	        Optional<Users> existingUserOptional = userRepository.findById(id);
	        if (existingUserOptional.isPresent()) {
	            Users existingUser = existingUserOptional.get();
	            existingUser.setFirstName(updatedUser.getFirstName());
	            existingUser.setLastName(updatedUser.getLastName());
	            existingUser.setEmail(updatedUser.getEmail());
	            return userRepository.save(existingUser);
	        } else {
	            throw new Exception("Utilisateur introuvable avec l'ID : " + id);
	        }
	        }

	  public List<Users> findByApprovedandRoleName(String roleName) {
	        try {
	            // Conversion de roleName en enum RoleName
	            return userRepository.findByIsApprovedAndRoleRoleName(true, roleName);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Role not found: " + roleName, e);
	        }
	    }
	 
	  public List<Users> findNotApprovedUsers(String firstName, String lastName) {
		    return userRepository.findByIsApprovedAndFirstNameContainingAndLastNameContaining(false, firstName, lastName);
		}

	  public List<Users> findApprovedUsers(String firstName, String lastName) {
		    return userRepository.findByIsApprovedAndFirstNameContainingAndLastNameContaining(true, firstName, lastName);
		}
	  
	//eya
	    
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

	    //ajoutercompte
	  public Users AddUser(SignupRequest signupRequest) {
		  if (userRepository.existsByEmail(signupRequest.getEmail())) {
	            throw new IllegalArgumentException("Email déjà utilisé");
	        }
	        Role role = roleRepository.findByRoleName(signupRequest.getRole());
			Users user = new Users();
	        user.setRole(role);
			user.setPassword(signupRequest.getPassword());
			user.setFirstName(signupRequest.getFirstName());
			user.setLastName(signupRequest.getLastName());
			user.setEmail(signupRequest.getEmail());
	        user.setVerified(true);
	        user.setApproved(false);
	        Users newUser = userRepository.save(user);
	        try {emailService.sendAdminAddUserEmail(newUser);
	        } catch (MessagingException e) {
		            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());

	        }
	        return newUser;

		    }
	  
	

	    //khedmtkkk
		public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
			Users ThisUser;
			System.out.println(authenticationRequest);
			var auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
			);
			var claims = new HashMap<String,Object>();
			var user = (Users)auth.getPrincipal();

			user.getAuthorities().forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));
			claims.put("fullName",user.getFullName());
			var jwtToken = jwtService.generateToken(claims,user);

			String role = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(" ");

			UserResponse ReturnedResponse = new UserResponse
					.UserResponseBuilder()
					.id(user.getId())
					.email(user.getEmail())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.role(role)
					.token(jwtToken)
					.build();

			return AuthenticationResponse.builder()
					.user(ReturnedResponse)
					.build();
		}
		public void generateAndSendEmailRestToken(String email) throws MessagingException {

			Optional<Users> userByEmail = userRepository.findByEmail(email);

			if(userByEmail.isPresent()){
				String generatedToken = generateActivationCode();
				var token = Token.builder()
						.token(generatedToken)
						.createdAt(LocalDateTime.now())
						.expiresAt(LocalDateTime.now().plusMinutes(15))
						.user(userByEmail.get())
						.build();
				tokenRepository.save(token);

				String resetLink = "http://localhost:4200/reset-password?token=" + token;

				System.out.println(resetLink);

				emailService.sendPasswordResetEmail(userByEmail.get(),token.getToken());
			}
		}
	  

		private String generateActivationCode() {
	        return UUID.randomUUID().toString();
		}

}



	
	 
	






