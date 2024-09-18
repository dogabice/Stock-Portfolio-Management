package com.spmapi.spmapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class UserRole {
    //----------------------------------------------------------------     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //---------------------------------------------------------------- 
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //---------------------------------------------------------------- 
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    //---------------------------------------------------------------- 
    // Constructors
    public UserRole() {
    }

    public UserRole(Long id, User user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }
    //---------------------------------------------------------------- 
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    //-------------------------------------------------
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    //-------------------------------------------------
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    //----------------------------------------------------------------     
}
