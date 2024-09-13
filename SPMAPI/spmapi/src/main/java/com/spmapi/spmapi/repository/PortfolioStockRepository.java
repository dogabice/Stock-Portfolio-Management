package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.PortfolioStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {
}
