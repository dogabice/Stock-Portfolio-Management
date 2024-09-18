package com.spmapi.spmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import com.spmapi.spmapi.DTOs.BuyStockDTO;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.model.User;
@Service
public class BuyStockService {
    //---------------------------------------------------------------- 
    //SERVICES
    @Autowired
    private UserService userService;
    //---------------------------------------------------------------- 
    @Autowired
    private PortfolioService portfolioService;
    //---------------------------------------------------------------- 
    @Autowired
    private StockService stockService;
    //---------------------------------------------------------------- 
    @Autowired
    private TransactionService transactionService;
    //----------------------------------------------------------------     
    public Transaction BuyStockDTOToTransaction(BuyStockDTO buyStockDTO) {
        Transaction transaction = new Transaction();
        //----------------------------------------------------------------     
        Optional<User> userOptional = userService.getUserById(buyStockDTO.getUser_id());
        Optional<Portfolio> portfolioOptional = portfolioService.getPortfolioById(buyStockDTO.getPortfolio_id());
        Optional<Stock> stockOptional = stockService.getStockById(buyStockDTO.getStock_id());
        //----------------------------------------------------------------     
        if (userOptional.isPresent() && portfolioOptional.isPresent() && stockOptional.isPresent()) {
            User user = userOptional.get();
            Portfolio portfolio = portfolioOptional.get();
            Stock stock = stockOptional.get();
            //----------------------------------------------------------------
            //Getting informations      
            BigDecimal buyPrice = stock.getBuyPrice();
            int quantity = buyStockDTO.getQuantity();
            BigDecimal commissionRate = transactionService.getCommissionRate();
            //----------------------------------------------------------------     
            //Calculating totalcost, commission and finalcost values.
            BigDecimal totalCost = buyPrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal commission = totalCost.multiply(commissionRate).divide(BigDecimal.valueOf(100));
            BigDecimal finalCost = totalCost.subtract(commission);
            //---------------------------------------------------------------- 
            //Setting informations    
            transaction.setUser(user);
            transaction.setPortfolio(portfolio);
            transaction.setStock(stock);
            transaction.setQuantity(quantity);
            transaction.setTransactionType(Transaction.TransactionType.BUY);
            transaction.setPrice(finalCost); // Toplam maliyeti fiyat olarak ayarla
            transaction.setCommission(commission); // Komisyonu ayarla
            transactionService.saveTransaction(transaction);
            
        } else {
            throw new RuntimeException("User, Portfolio or Stock cannot be found.");
        }
        
        return transaction;
    }
    
    
}
