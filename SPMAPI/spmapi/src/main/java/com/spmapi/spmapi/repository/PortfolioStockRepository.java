package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.PortfolioStock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List; 


@Repository
public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {
      Optional<PortfolioStock> findById(Long id);

      List<PortfolioStock> findByPortfolioId(Long portfolioId);
}
