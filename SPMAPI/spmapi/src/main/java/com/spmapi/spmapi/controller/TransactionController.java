package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.BuyStockDTO;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.service.BuyStockService;
import com.spmapi.spmapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PutMapping("/update-commission")
    public ResponseEntity<String> updateCommissionRate(@RequestParam BigDecimal newRate) {
        if (newRate.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Commission rate must be larger than zero.");
        }

        transactionService.setCommissionRate(newRate);
        return ResponseEntity.ok("Commission rate successfully updated: " + newRate + "%");
    }

    @GetMapping("/commission-rate")
    public ResponseEntity<BigDecimal> getCommissionRate() {
        return ResponseEntity.ok(transactionService.getCommissionRate());
    }
}
