package com.propilot.performance_management_app.model;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class Users {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String email;

	    @Column(nullable = false)
	    private String password;

	    @Column(name = "first_name")
	    private String firstName;

	    @Column(name = "last_name")
	    private String lastName;

	    @ManyToOne // Chaque utilisateur a un seul rôle
	    @JoinColumn(name = "role_id", nullable = false)
	    private Role role; // Le rôle de l'utilisateur

	    @Column(name = "is_verified", nullable = false)
	    private boolean isVerified;
	    @Column(name = "is_approved", nullable = false)
	    private boolean isApproved = false; // Par défaut, non approuvé

	    @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDateTime createdAt = LocalDateTime.now();
	    private String verificationToken;
	    // Getters et setters

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getFirstName() {
	        return firstName;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public String getLastName() {
	        return lastName;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }

	    public Role getRole() {
	        return role;
	    }

	    public void setRole(Role role) {
	        this.role = role;
	    }
	    public boolean isVerified() {
	        return isVerified;
	    }

	    public void setVerified(boolean isVerified) {
	        this.isVerified = isVerified;
	    }
	    public boolean isApproved() {
	        return isApproved;
	    }

	    public void setApproved(boolean approved) {
	        isApproved = approved;
	    }
	    public String getVerificationToken() {
	        return verificationToken;
	    }

	    public void setVerificationToken(String verificationToken) {
	        this.verificationToken = verificationToken;
	    }
	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }

}
