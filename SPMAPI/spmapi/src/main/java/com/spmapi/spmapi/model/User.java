package com.spmapi.spmapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "User_of_SPMAPI") 
public class User {
    //----------------------------------------------------------------     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    //---------------------------------------------------------------- 
    private LocalDateTime createdAt;
    private String role; 
    private BigDecimal balance; 
    //---------------------------------------------------------------- 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Portfolio> portfolios;
    //---------------------------------------------------------------- 
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
    //---------------------------------------------------------------- 
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Balance> balances;
    //---------------------------------------------------------------- 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBalanceCode> userBalanceCodes;
    //---------------------------------------------------------------- 
     // Constructors
     public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String username, String password ,LocalDateTime createdAt,
     String role, BigDecimal balance,
     List<Portfolio> portfolios, List<Transaction> transactions, 
     List<Balance> balances, List<UserBalanceCode> userBalanceCodes) {
        this.id = id;
        this.username = username;
        this.password=password;
        this.createdAt=createdAt;
        this.role = role;
        this.balance = balance;
        this.portfolios = portfolios;
        this.transactions=transactions;
        this.balances=balances;
        this.userBalanceCodes=userBalanceCodes;
    }
    //----------------------------------------------------------------  
    //To fill createdAt value
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    //---------------------------------------------------------------- 
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //-------------------------------------------------------------------
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    //-------------------------------------------------------------------
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    //-------------------------------------------------------------------
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    //-------------------------------------------------------------------
    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }
    //-------------------------------------------------------------------
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    //-------------------------------------------------------------------
    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }
    //-------------------------------------------------------------------
    public List<UserBalanceCode> getUserBalanceCodes() {
        return userBalanceCodes;
    }

    public void setUserBalanceCodes(List<UserBalanceCode> userBalanceCodes) {
        this.userBalanceCodes = userBalanceCodes;
    }
    //-------------------------------------------------------------------
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    //-------------------------------------------------------------------
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
