package com.spmapi.spmapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class PortfolioStock {
    //----------------------------------------------------------------    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //----------------------------------------------------------------
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    //----------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    //----------------------------------------------------------------
    private int quantity;
    //----------------------------------------------------------------
    // Constructors
    public PortfolioStock() {
    }

    public PortfolioStock(Long id, Portfolio portfolio, Stock stock, int quantity) {
        this.id = id;
        this.portfolio = portfolio;
        this.stock = stock;
        this.quantity = quantity;
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
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    //----------------------------------------------------------------    
}
