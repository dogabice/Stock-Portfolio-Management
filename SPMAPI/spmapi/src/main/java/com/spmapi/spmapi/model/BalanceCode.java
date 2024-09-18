package com.spmapi.spmapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table
public class BalanceCode {
    //----------------------------------------------------------------    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //----------------------------------------------------------------
    private String code;
    private BigDecimal amount;
    //----------------------------------------------------------------
    // Constructors
    public BalanceCode() {
    }

    public BalanceCode(Long id, String code, BigDecimal amount) {
        this.id = id;
        this.code = code;
        this.amount = amount;
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
