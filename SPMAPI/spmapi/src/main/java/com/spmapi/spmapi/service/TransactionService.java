package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    //----------------------------------------------------------------    
    @Autowired
    private TransactionRepository transactionRepository;
    //---------------------------------------------------------------- 
    //Check the default commission rate value from application.properties file.
    @Value("${commission.rate}")
    private BigDecimal commissionRate; 
    //---------------------------------------------------------------- 
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByCreatedAtDesc();
    }    
    //---------------------------------------------------------------- 
    public Transaction saveTransaction(Transaction transaction) {
        BigDecimal commission = calculateCommission(transaction.getPrice(), transaction.getQuantity());
        transaction.setCommission(commission);
        return transactionRepository.save(transaction);
    }
    //---------------------------------------------------------------- 
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
    //---------------------------------------------------------------- 
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
    //---------------------------------------------------------------- 
    private BigDecimal calculateCommission(BigDecimal price, int quantity) {
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
        return totalPrice.multiply(commissionRate).divide(BigDecimal.valueOf(100));
    }
    //---------------------------------------------------------------- 
    // To use of Admin's transaction of changing commission rate. 
    public void setCommissionRate(BigDecimal newRate) {
        this.commissionRate = newRate;
    }
    // To get the commission rate value. 
    public BigDecimal getCommissionRate() {
        return this.commissionRate;
    }
    //----------------------------------------------------------------
    
}
