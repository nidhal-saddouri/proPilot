package com.propilot.performance_management_app.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String roleName;
	    @JsonIgnore 
	    @OneToMany(mappedBy = "role")  // Relation One-to-Many avec User
	    private List<Users> users;  // Un rôle peut être attribué à plusieurs utilisateurs


	    // Getter for roleName


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
	        this.roleName = roleName;
	    }
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public List<Users> getUsers() {
	        return users;
	    }

	    public void setUsers(List<Users> users) {
	        this.users = users;
	    }
}
