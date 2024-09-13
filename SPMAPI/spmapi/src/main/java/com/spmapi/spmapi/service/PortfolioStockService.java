package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.PortfolioStock;
import com.spmapi.spmapi.repository.PortfolioStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioStockService {
    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

    public List<PortfolioStock> getAllPortfolioStocks() {
        return portfolioStockRepository.findAll();
    }

    public PortfolioStock savePortfolioStock(PortfolioStock portfolioStock) {
        return portfolioStockRepository.save(portfolioStock);
    }

    public void deletePortfolioStock(Long id) {
        portfolioStockRepository.deleteById(id);
    }
}
