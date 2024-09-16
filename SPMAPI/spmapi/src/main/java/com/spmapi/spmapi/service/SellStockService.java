package com.spmapi.spmapi.service;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.spmapi.spmapi.DTOs.SellStockDTO;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.model.Transaction.TransactionType;


@Service
public class SellStockService {

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    public Transaction SellStockDTOToTransaction(SellStockDTO sellStockDTO) {
        Transaction transaction = new Transaction();
        
        Optional<User> userOptional = userService.getUserById(sellStockDTO.user_id);
        Optional<Portfolio> portfolioOptional = portfolioService.getPortfolioById(sellStockDTO.portfolio_id);
        Optional<Stock> stockOptional = stockService.getStockById(sellStockDTO.stock_id);
    
        // Eğer user varsa işlemi gerçekleştir
        if (userOptional.isPresent() && portfolioOptional.isPresent() && stockOptional.isPresent()) {
            User user = userOptional.get();
            Portfolio portfolio = portfolioOptional.get();
            Stock stock = stockOptional.get();
            //TransactionType transactionType = transaction.getTransactionType();
    
            transaction.setUser(user); // Artık Optional'dan çıkarılmış User nesnesini kullanabilirsiniz.
            transaction.setPortfolio(portfolio);
            transaction.setStock(stock);
            transaction.setQuantity(sellStockDTO.quantity);
            transaction.setPrice(sellStockDTO.price);
            transaction.setTransactionType(TransactionType.SELL);
        } else {
            // Eğer verilerden biri eksikse hata fırlatabilirsiniz veya başka bir işlem yapabilirsiniz
            throw new RuntimeException("User, Portfolio veya Stock bulunamadı");
        }
    
        return transaction;
    }
    
}
