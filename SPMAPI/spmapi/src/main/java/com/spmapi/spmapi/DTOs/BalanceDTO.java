package com.spmapi.spmapi.DTOs;

import java.math.BigDecimal;

public class BalanceDTO {
    private BigDecimal balance;
    private Long userId; // İlişkili User ID'si
    //----------------------------------------------------------------
    // Default constructor
    public BalanceDTO() {
    }
    //----------------------------------------------------------------
    // Constructor with parameters
    public BalanceDTO(BigDecimal balance, Long userId) {
        this.balance = balance;
        this.userId = userId;
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
