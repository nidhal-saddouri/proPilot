package com.propilot.performance_management_app.controller;

import com.propilot.performance_management_app.DTO.EmailRequest;
import com.propilot.performance_management_app.DTO.SignupRequest;
import com.propilot.performance_management_app.model.Token;
import com.propilot.performance_management_app.model.Users;
import com.propilot.performance_management_app.repository.TokenRepository;
import com.propilot.performance_management_app.repository.UserRepository;
import com.propilot.performance_management_app.service.AuthServiceImpl;
import com.propilot.performance_management_app.service.AuthenticationRequest;
import com.propilot.performance_management_app.service.AuthenticationResponse;
import com.propilot.performance_management_app.service.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("auth")
public class authController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrybt;

	@Autowired
private UserServiceImpl userservice;

	@Autowired
    private AuthServiceImpl authService;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ){
        return ResponseEntity.ok(userservice.authenticate(authenticationRequest));
    }

	  @PostMapping("/register")
	    public ResponseEntity<?> signup(@RequestBody SignupRequest user) {
          System.out.println(user.getRole());
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

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailRequest email) throws MessagingException {
    	userservice.generateAndSendEmailRestToken(email.getEmail());
        return ResponseEntity.ok("Password reset link sent to your email!");
    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        Token resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired");
        }

        return ResponseEntity.ok("Token verified. Display password reset form.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Token resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired");
        }

        Users user = resetToken.getUser();
        user.setPassword(bcrybt.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken);

        return ResponseEntity.ok("Password successfully reset!");
    }



}
