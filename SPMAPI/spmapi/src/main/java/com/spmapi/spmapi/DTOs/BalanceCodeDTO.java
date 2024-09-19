package com.spmapi.spmapi.DTOs;

import java.math.BigDecimal;

public class BalanceCodeDTO {
    private String code;
    private BigDecimal amount;
    //----------------------------------------------------------------
    // Default constructor
    public BalanceCodeDTO() {
    }
    //----------------------------------------------------------------
    // Constructor with parameters
    public BalanceCodeDTO(String code, BigDecimal amount) {
        this.code = code;
        this.amount = amount;
    }
    //----------------------------------------------------------------
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    //----------------------------------------------------------------
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    //----------------------------------------------------------------
}
