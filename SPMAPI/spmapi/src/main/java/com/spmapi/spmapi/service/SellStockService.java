package com.spmapi.spmapi.service;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.spmapi.spmapi.DTOs.SellStockDTO;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.model.User;


@Service
public class SellStockService {
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
    public Transaction SellStockDTOToTransaction(SellStockDTO sellStockDTO) {
        Transaction transaction = new Transaction();
        //---------------------------------------------------------------- 
        Optional<User> userOptional = userService.getUserById(sellStockDTO.getUser_id());
        Optional<Portfolio> portfolioOptional = portfolioService.getPortfolioById(sellStockDTO.getPortfolio_id());
        Optional<Stock> stockOptional = stockService.getStockById(sellStockDTO.getStock_id());
        //---------------------------------------------------------------- 
        // Check that there is a user?
        if (userOptional.isPresent() && portfolioOptional.isPresent() && stockOptional.isPresent()) {
            User user = userOptional.get();
            Portfolio portfolio = portfolioOptional.get();
            Stock stock = stockOptional.get();
            //---------------------------------------------------------------- 
            BigDecimal sellPrice = stock.getBuyPrice();
            int quantity = sellStockDTO.getQuantity();
            BigDecimal commissionRate = transactionService.getCommissionRate();
            //---------------------------------------------------------------- 
            BigDecimal totalCost = sellPrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal commission = totalCost.multiply(commissionRate).divide(BigDecimal.valueOf(100));
            BigDecimal finalCost = totalCost.subtract(commission);
            //---------------------------------------------------------------- 
            transaction.setUser(user);
            transaction.setPortfolio(portfolio);
            transaction.setStock(stock);
            transaction.setQuantity(quantity);
            transaction.setTransactionType(Transaction.TransactionType.SELL);
            transaction.setPrice(finalCost); // Toplam maliyeti fiyat olarak ayarla
            transaction.setCommission(commission); // Komisyonu ayarla
            transactionService.saveTransaction(transaction);
            
        } else {
            throw new RuntimeException("User, Portfolio or Stock cannot be found.");
        }
        
        return transaction;
    }
    
}
