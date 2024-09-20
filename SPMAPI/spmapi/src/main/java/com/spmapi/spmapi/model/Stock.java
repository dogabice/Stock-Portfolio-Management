package com.spmapi.spmapi.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "stock", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Stock {
    //----------------------------------------------------------------    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    //----------------------------------------------------------------   
    @Column(name = "last_price")
    private BigDecimal lastPrice;
    //----------------------------------------------------------------   
    @Column(name = "buy_price")
    private BigDecimal buyPrice;
    //----------------------------------------------------------------   
    @Column(name = "sell_price")
    private BigDecimal sellPrice;
    //----------------------------------------------------------------   
    @Column(name = "symbol")
    private String symbol;
    //----------------------------------------------------------------   
    @OneToMany(mappedBy = "stock")
    @JsonIgnore
    private List<PortfolioStock> portfolioStocks;
    //----------------------------------------------------------------   
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;
    //----------------------------------------------------------------   
    // Constructors
    public Stock() {
    }

    public Stock(Long id, String name, BigDecimal lastPrice, BigDecimal buyPrice, BigDecimal sellPrice, String symbol) {
        this.id = id;
        this.name = name;
        this.lastPrice=lastPrice;
        this.buyPrice=buyPrice;
        this.sellPrice=sellPrice;
        this.symbol=symbol;
    }
    //----------------------------------------------------------------   
    // Getters and Setters
    //-------------------------------------------------
    //stock id 
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    //-------------------------------------------------
    //stock name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //-------------------------------------------------
    //stock's last price 
    public BigDecimal getLastPrice() {
        return lastPrice;
    }
    public void setLastPrice(BigDecimal last_price) {
        this.lastPrice = last_price;
    }
    //-------------------------------------------------
    //stock's buy price 
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }
    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }
    //-------------------------------------------------
    //stock's sell price 
    public BigDecimal getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }
    //-------------------------------------------------
}
