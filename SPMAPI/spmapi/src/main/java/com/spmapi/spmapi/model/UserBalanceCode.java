package com.spmapi.spmapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserBalanceCode {
    //----------------------------------------------------------------     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //---------------------------------------------------------------- 
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    //---------------------------------------------------------------- 
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "balance_code_id")
    private BalanceCode balanceCode;
    //---------------------------------------------------------------- 
    private boolean used;
    //---------------------------------------------------------------- 
    // Constructors
    public UserBalanceCode() {}

    public UserBalanceCode(Long id, User user, BalanceCode balanceCode, boolean used) {
        this.id = id;
        this.user = user;
        this.balanceCode = balanceCode;
        this.used = used;
    }
    //---------------------------------------------------------------- 
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //---------------------------------------------------------------- 
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //---------------------------------------------------------------- 
    public BalanceCode getBalanceCode() {
        return balanceCode;
    }

    public void setBalanceCode(BalanceCode balanceCode) {
        this.balanceCode = balanceCode;
    }
    //---------------------------------------------------------------- 
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    //----------------------------------------------------------------     
}
