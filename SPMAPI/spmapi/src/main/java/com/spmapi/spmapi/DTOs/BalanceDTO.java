package com.spmapi.spmapi.DTOs;

import java.math.BigDecimal;

public class BalanceDTO {
    private Long id;
    private BigDecimal balance;
    private Long userId; // İlişkili User ID'si
    //----------------------------------------------------------------
    // Default constructor
    public BalanceDTO() {
    }
    //----------------------------------------------------------------
    // Constructor with parameters
    public BalanceDTO(Long id, BigDecimal balance, Long userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
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
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    //----------------------------------------------------------------
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    //----------------------------------------------------------------
}
