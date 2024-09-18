package com.spmapi.spmapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class Portfolio {
    //----------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //----------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //----------------------------------------------------------------
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioStock> portfolioStocks;
    //----------------------------------------------------------------
    // Constructors
    public Portfolio() {
    }

    public Portfolio(Long id, User user, List<PortfolioStock> portfolioStocks) {
        this.id = id;
        this.user = user;
        this.portfolioStocks = portfolioStocks;
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
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //----------------------------------------------------------------
    public List<PortfolioStock> getPortfolioStocks() {
        return portfolioStocks;
    }

    public void setPortfolioStocks(List<PortfolioStock> portfolioStocks) {
        this.portfolioStocks = portfolioStocks;
    }
    //----------------------------------------------------------------
}
