package com.spmapi.spmapi.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spmapi.spmapi.DTOs.CreateUserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "User_of_SPMAPI") 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    private LocalDateTime createdAt;
    private String role; 
    private double balance; 

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Balance> balances;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBalanceCode> userBalanceCodes;



    

     // Parametresiz yapıcı
     public User() {
    }

    // ID ile yapıcı
    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String username, String password ,LocalDateTime createdAt,
     String role, double balance,
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

    // Getters and Setters
    //-------------------------------------------------------------------
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
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
