package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.PortfolioStock;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.repository.PortfolioRepository;
import com.spmapi.spmapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    //----------------------------------------------------------------     
    @Autowired
    private PortfolioRepository portfolioRepository;
   //----------------------------------------------------------------
    @Autowired
    private UserRepository userRepository;
    //---------------------------------------------------------------- 
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
    //---------------------------------------------------------------- 
    public Optional<Portfolio> getPortfolioById(Long id) {
        return portfolioRepository.findById(id);
    }
    //---------------------------------------------------------------- 
    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }
    //---------------------------------------------------------------- 
    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }
    //----------------------------------------------------------------   
    //System already create one portfolio for each user but with use of this method they can create more portfolio for themselfs.  
    public void createPortfolioForUser(User user) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setPortfolioStocks(new ArrayList<>()); 
        portfolioRepository.save(portfolio);
    }
    //---------------------------------------------------------------- 
    public Portfolio userCreatePortfolio(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User cannot be found."));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setPortfolioStocks(new ArrayList<>());

        return portfolioRepository.save(portfolio);
    }
    public boolean hasSufficientStocks(Portfolio portfolio, Stock stock, int quantity) {
    Optional<PortfolioStock> portfolioStockOptional = portfolio.getPortfolioStocks().stream()
        .filter(ps -> ps.getStock().equals(stock))
        .findFirst();

    if (portfolioStockOptional.isPresent()) {
        PortfolioStock portfolioStock = portfolioStockOptional.get();
        return portfolioStock.getQuantity() >= quantity;
    }

    return false;
    }
    //---------------------------------------------------------------- 
   
}
