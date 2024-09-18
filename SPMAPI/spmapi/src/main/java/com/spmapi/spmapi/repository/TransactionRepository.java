package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByPortfolioId(Long portfolioId);

    List<Transaction> findByStockId(Long stockId);

    List<Transaction> findAllByOrderByCreatedAtDesc();
}
