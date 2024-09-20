package com.spmapi.spmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import com.spmapi.spmapi.DTOs.BuyStockDTO;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.PortfolioStock;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.model.UserBalanceCode;
import com.spmapi.spmapi.repository.PortfolioStockRepository;

@Service
public class BuyStockService {
    //---------------------------------------------------------------- 
    //SERVICES
    @Autowired
    private UserService userService;
    //---------------------------------------------------------------- 
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioStockRepository portfolioStockRepository;
    //---------------------------------------------------------------- 
    @Autowired
    private StockService stockService;
    //---------------------------------------------------------------- 
    @Autowired
    private TransactionService transactionService;
    //---------------------------------------------------------------- 
    @Autowired
    private UserBalanceCodeService userBalanceCodeService;
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
            //Check if the user has sufficient funds
            List<UserBalanceCode> unusedBalanceCodes = userBalanceCodeService.getUnusedBalanceCodesByUserId(buyStockDTO.getUser_id());
            BigDecimal totalAvailableBalance = unusedBalanceCodes.stream()
                .map(code -> code.getBalanceCode().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            System.out.println("Total Available Balance: " + totalAvailableBalance);
            System.out.println("Final Cost: " + finalCost);

            if (totalAvailableBalance.compareTo(finalCost) >= 0) {
                throw new RuntimeException("Insufficient funds: Transactions cannot be made.");
            }
            //---------------------------------------------------------------- 
            //Setting informations    
            transaction.setUser(user);
            transaction.setPortfolio(portfolio);
            transaction.setStock(stock);
            transaction.setQuantity(quantity);
            transaction.setTransactionType(Transaction.TransactionType.BUY);
            transaction.setPrice(finalCost); 
            transaction.setCommission(commission); 
            transactionService.saveTransaction(transaction);
            //----------------------------------------------------------------
            
            Optional<PortfolioStock> portfolioStockOptional = portfolio.getPortfolioStocks().stream()
            .filter(ps -> ps.getStock().equals(stock))
            .findFirst();

            if (portfolioStockOptional.isPresent()) {
                PortfolioStock portfolioStock = portfolioStockOptional.get();
                portfolioStock.setQuantity(portfolioStock.getQuantity() + quantity);
                portfolioStockRepository.save(portfolioStock);
            } else {
                PortfolioStock newPortfolioStock = new PortfolioStock();
                newPortfolioStock.setPortfolio(portfolio);
                newPortfolioStock.setStock(stock);
                newPortfolioStock.setQuantity(quantity);
                portfolioStockRepository.save(newPortfolioStock);
            }

            //Deduction from user balance and sign the code that been used.
            BigDecimal remainingAmount = finalCost;
            for (UserBalanceCode userBalanceCode : unusedBalanceCodes) {
                BigDecimal codeAmount = userBalanceCode.getBalanceCode().getAmount();

                if (remainingAmount.compareTo(codeAmount) >= 0) {
                    remainingAmount = remainingAmount.subtract(codeAmount);
                    userBalanceCodeService.markBalanceCodeAsUsed(userBalanceCode);
                } else {
                    userBalanceCode.getBalanceCode().setAmount(codeAmount.subtract(remainingAmount));
                    remainingAmount = BigDecimal.ZERO;
                    userBalanceCodeService.saveUserBalanceCode(userBalanceCode);
                    break;
                }
            }
            userBalanceCodeService.deductFromUserBalance(user, finalCost);
            
        } else {
            throw new RuntimeException("User, Portfolio or Stock cannot be found.");
        }
        
        return transaction;
    }
    
    
}
