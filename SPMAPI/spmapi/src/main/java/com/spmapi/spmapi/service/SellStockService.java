package com.spmapi.spmapi.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import com.spmapi.spmapi.DTOs.SellStockDTO;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.PortfolioStock;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.repository.PortfolioStockRepository;

@Service
public class SellStockService {
    // SERVICES
    @Autowired
    private UserService userService;
    
    @Autowired
    private PortfolioService portfolioService;
    
    @Autowired
    private PortfolioStockRepository portfolioStockRepository;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private UserBalanceCodeService userBalanceCodeService; // Yeni eklenen servis

    public Transaction SellStockDTOToTransaction(SellStockDTO sellStockDTO) {
        Transaction transaction = new Transaction();

        Optional<User> userOptional = userService.getUserById(sellStockDTO.getUser_id());
        Optional<Portfolio> portfolioOptional = portfolioService.getPortfolioById(sellStockDTO.getPortfolio_id());
        Optional<Stock> stockOptional = stockService.getStockById(sellStockDTO.getStock_id());

        // Kontroller
        if (userOptional.isPresent() && portfolioOptional.isPresent() && stockOptional.isPresent()) {
            User user = userOptional.get();
            Portfolio portfolio = portfolioOptional.get();
            Stock stock = stockOptional.get();

            BigDecimal sellPrice = stock.getSellPrice(); // Satış fiyatı
            int quantity = sellStockDTO.getQuantity();
            BigDecimal commissionRate = transactionService.getCommissionRate();

            // Gelir hesaplama
            BigDecimal totalIncome = sellPrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal commission = totalIncome.multiply(commissionRate).divide(BigDecimal.valueOf(100));
            BigDecimal finalIncome = totalIncome.subtract(commission);

            // Transaction objesini ayarlama
            transaction.setUser(user);
            transaction.setPortfolio(portfolio);
            transaction.setStock(stock);
            transaction.setQuantity(quantity);
            transaction.setTransactionType(Transaction.TransactionType.SELL);
            transaction.setPrice(finalIncome); // Satış fiyatı
            transaction.setCommission(commission); // Komisyon
            transactionService.saveTransaction(transaction);

            // Portföydeki hisse miktarını güncelle
            Optional<PortfolioStock> portfolioStockOptional = portfolio.getPortfolioStocks().stream()
                .filter(ps -> ps.getStock().equals(stock))
                .findFirst();

            if (portfolioStockOptional.isPresent()) {
                PortfolioStock portfolioStock = portfolioStockOptional.get();
                
                // Miktarı kontrol et
                if (portfolioStock.getQuantity() < quantity) {
                    throw new RuntimeException("Not enough stocks in portfolio to sell.");
                }

                portfolioStock.setQuantity(portfolioStock.getQuantity() - quantity);
                portfolioStockRepository.save(portfolioStock);
            } else {
                throw new RuntimeException("Stock not found in portfolio.");
            }

            // Kullanıcının bakiyesini güncelle
            updateUserBalance(user, finalIncome);

        } else {
            throw new RuntimeException("User, Portfolio or Stock cannot be found.");
        }
        
        return transaction;
    }

    private void updateUserBalance(User user, BigDecimal amount) {
        // Kullanıcının bakiyesini güncelleme
        userBalanceCodeService.addToUserBalance(user, amount);
    }
}
