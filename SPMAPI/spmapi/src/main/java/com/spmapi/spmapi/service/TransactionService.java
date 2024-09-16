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
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${commission.rate}")
    private BigDecimal commissionRate; // Varsayılan oranı property'den alır.

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        BigDecimal commission = calculateCommission(transaction.getPrice(), transaction.getQuantity());
        transaction.setCommission(commission);
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    private BigDecimal calculateCommission(BigDecimal price, int quantity) {
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
        return totalPrice.multiply(commissionRate).divide(BigDecimal.valueOf(100));
    }

    // Admin'in komisyon oranını değiştirebileceği setter metodu
    public void setCommissionRate(BigDecimal newRate) {
        this.commissionRate = newRate;
    }

    // Mevcut komisyon oranını almak için getter metodu
    public BigDecimal getCommissionRate() {
        return this.commissionRate;
    }
}
