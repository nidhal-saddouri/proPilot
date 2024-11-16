package com.example.ProPilot.model;

import java.util.List;

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

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false, unique = true)
	    private RoleName roleName;

	    @OneToMany(mappedBy = "role")  // Relation One-to-Many avec User
	    private List<Users> users;  // Un rôle peut être attribué à plusieurs utilisateurs

	    public enum RoleName {
	        EMPLOYEE,
	        MANAGER
	    }
	    // Getter for roleName
	    public RoleName getRoleName() {
	        return roleName;
	    }

	    // Setter for roleName
	    public void setRoleName(RoleName roleName) {
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