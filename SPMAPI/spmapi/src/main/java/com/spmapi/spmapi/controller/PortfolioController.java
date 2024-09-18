package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    //-------------------------------------------------------------------
    @Autowired
    private PortfolioService portfolioService;
    //-------------------------------------------------------------------
    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }
    //-------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolioById(id);
        return portfolio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //-------------------------------------------------------------------
    @PostMapping
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.savePortfolio(portfolio);
    }
    //-------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        if (portfolioService.getPortfolioById(id).isPresent()) {
            portfolioService.deletePortfolio(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //-------------------------------------------------------------------
}
