package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    // Belirli bir sembole sahip stokları bulmak için
    Optional<Stock> findBySymbol(String symbol);

    // Belirli bir isme sahip stokları bulmak için
    Optional<Stock> findByName(String name);
}
