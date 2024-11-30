package com.propilot.performance_management_app.model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users")
public class Users implements UserDetails, Principal {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String email;

	    @Column(nullable = false)
	    private String password;

	    @Column(name = "first_name")
	    private String firstName;

	    @Column(nullable = false)
	    private boolean status;
	    
	    @Column(name = "last_name")
	    private String lastName;


		@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
		private Token restToken;

	
		@JsonIgnore
	    @ManyToOne // Chaque utilisateur a un seul rôle
	    @JoinColumn(name = "role_id", nullable = false)
	    private Role role; // Le rôle de l'utilisateur

	    @Column(name = "is_verified", nullable = false)
	    private boolean isVerified;
	    @Column(name = "is_approved", nullable = false)
	    private boolean isApproved = false;

	    @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDateTime createdAt = LocalDateTime.now();

	   



	public Token getRestToken() {
		return restToken;
	}

	public void setRestToken(Token restToken) {
		this.restToken = restToken;
	}


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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
	}


	public String getPassword() {
	        return password;
	    }

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !isApproved;
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
	  
	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }
	    public boolean isLoggedIn() {
	        return status;
	    }

	    public void setLoggedIn(boolean isLoggedIn) {
	        this.status = isLoggedIn;
	    }

	@Override
	public String getName() {
		return "";
	}

	public String getFullName() {
			return this.firstName+" "+this.lastName;
	}
}
