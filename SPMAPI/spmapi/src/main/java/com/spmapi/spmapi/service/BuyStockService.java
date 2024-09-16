package com.spmapi.spmapi.service;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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

    public Transaction BuyStockDTOToTransaction(BuyStockDTO buyStockDTO) {
        Transaction transaction = new Transaction();
        
        Optional<User> userOptional = userService.getUserById(buyStockDTO.user_id);
        Optional<Portfolio> portfolioOptional = portfolioService.getPortfolioById(buyStockDTO.portfolio_id);
        Optional<Stock> stockOptional = stockService.getStockById(buyStockDTO.stock_id);
    
        // Eğer user varsa işlemi gerçekleştir
        if (userOptional.isPresent() && portfolioOptional.isPresent() && stockOptional.isPresent()) {
            User user = userOptional.get();
            Portfolio portfolio = portfolioOptional.get();
            Stock stock = stockOptional.get();
            //TransactionType transactionType = transaction.getTransactionType();
    
            transaction.setUser(user); // Artık Optional'dan çıkarılmış User nesnesini kullanabilirsiniz.
            transaction.setPortfolio(portfolio);
            transaction.setStock(stock);
            transaction.setQuantity(buyStockDTO.quantity);
            transaction.setPrice(buyStockDTO.price);
            transaction.setCommission(buyStockDTO.commission);
            transaction.setTransactionType(TransactionType.BUY);
        } else {
            // Eğer verilerden biri eksikse hata fırlatabilirsiniz veya başka bir işlem yapabilirsiniz
            throw new RuntimeException("User, Portfolio veya Stock bulunamadı");
        }
    
        return transaction;
    }
    
}
