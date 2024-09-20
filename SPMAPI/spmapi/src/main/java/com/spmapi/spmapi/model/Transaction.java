package com.spmapi.spmapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Transaction {
    //----------------------------------------------------------------      
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //----------------------------------------------------------------  
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    //----------------------------------------------------------------  
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "stock_id")
    private Stock stock;
    //----------------------------------------------------------------  
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id") // Kullanıcıyı tanımlamak için gerekli
    private User user;
    //----------------------------------------------------------------  
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    //----------------------------------------------------------------  
    private int quantity;
    private BigDecimal price;
    private BigDecimal commission;
    private LocalDateTime createdAt;
    //----------------------------------------------------------------  
    public enum TransactionType {
        BUY, SELL
    }
    //----------------------------------------------------------------  
    //To fill createdAt value
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    //----------------------------------------------------------------  
    // Getters, Setters, Constructors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //----------------------------------------------------------------  
    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
    //---------------------------------------------------------------- 
    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
    //---------------------------------------------------------------- 
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //---------------------------------------------------------------- 
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    //---------------------------------------------------------------- 
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    //---------------------------------------------------------------- 
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    //---------------------------------------------------------------- 
    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
    //---------------------------------------------------------------- 
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    //---------------------------------------------------------------- 
}
