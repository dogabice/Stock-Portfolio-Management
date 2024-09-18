package com.spmapi.spmapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table
public class Balance {
    //----------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //----------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //----------------------------------------------------------------
    private BigDecimal balance;
    //----------------------------------------------------------------
    // Constructors
    public Balance() {
    }

    public Balance(Long id, User user, BigDecimal balance) {
        this.id = id;
        this.user = user;
        this.balance = balance;
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
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    //----------------------------------------------------------------    
}
