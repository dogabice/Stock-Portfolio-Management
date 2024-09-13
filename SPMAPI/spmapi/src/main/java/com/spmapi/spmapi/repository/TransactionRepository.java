package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Belirli bir kullanıcıya ait işlemleri listeleme
    List<Transaction> findByUserId(Long userId);

    // Belirli bir portföydeki işlemleri listeleme
    List<Transaction> findByPortfolioId(Long portfolioId);

    // Belirli bir stok ile ilgili işlemleri listeleme
    List<Transaction> findByStockId(Long stockId);
}
