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
import com.spmapi.spmapi.model.Transaction.TransactionType;

@Service
public class BuyStockService {

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    @Autowired
    private TransactionService transactionService;

    public void buyStock(BuyStockDTO buyStockDTO) {
        BigDecimal buyPrice = stockService.getBuyPriceByStockId(buyStockDTO.getStock_id());
        int quantity = buyStockDTO.getQuantity();
        BigDecimal commissionRate = transactionService.getCommissionRate();
        
        BigDecimal totalCost = buyPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal commission = totalCost.multiply(commissionRate).divide(BigDecimal.valueOf(100));
        BigDecimal finalCost = totalCost.subtract(commission);
        
        
        Transaction transaction = BuyStockDTOToTransaction(buyStockDTO);
        transaction.setPrice(finalCost); // Toplam maliyeti fiyat olarak ayarla
        transaction.setCommission(commission); // Komisyonu ayarla
        
        transactionService.saveTransaction(transaction);
    }
    
    public Transaction BuyStockDTOToTransaction(BuyStockDTO buyStockDTO) {
        Transaction transaction = new Transaction();
        
        Optional<User> userOptional = userService.getUserById(buyStockDTO.getUser_id());
        Optional<Portfolio> portfolioOptional = portfolioService.getPortfolioById(buyStockDTO.getPortfolio_id());
        Optional<Stock> stockOptional = stockService.getStockById(buyStockDTO.getStock_id());
        
        if (userOptional.isPresent() && portfolioOptional.isPresent() && stockOptional.isPresent()) {
            User user = userOptional.get();
            Portfolio portfolio = portfolioOptional.get();
            Stock stock = stockOptional.get();
            
            transaction.setUser(user);
            transaction.setPortfolio(portfolio);
            transaction.setStock(stock);
            transaction.setQuantity(buyStockDTO.getQuantity());
            transaction.setTransactionType(Transaction.TransactionType.BUY);
            // Burada price, toplam maliyeti ifade eder
            transaction.setPrice(stock.getBuyPrice().multiply(BigDecimal.valueOf(buyStockDTO.getQuantity())));
        } else {
            throw new RuntimeException("User, Portfolio or Stock cannot be found.");
        }
        
        return transaction;
    }
    
}
