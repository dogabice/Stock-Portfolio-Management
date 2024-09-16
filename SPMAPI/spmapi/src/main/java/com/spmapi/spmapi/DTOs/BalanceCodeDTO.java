package com.spmapi.spmapi.DTOs;

import java.math.BigDecimal;

public class BalanceCodeDTO {
    private Long id;
    private String code;
    private BigDecimal amount;

    // Default constructor
    public BalanceCodeDTO() {
    }

    // Constructor with parameters
    public BalanceCodeDTO(Long id, String code, BigDecimal amount) {
        this.id = id;
        this.code = code;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
