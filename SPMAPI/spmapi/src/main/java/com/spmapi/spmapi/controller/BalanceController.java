package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.BalanceDTO;
import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/balances")
public class BalanceController {
    //-------------------------------------------------------------------     
    @Autowired
    private BalanceService balanceService;
    //------------------------------------------------------------------- 
    //MAPPINGS
    @GetMapping
    public ResponseEntity<List<BalanceDTO>> getAllBalances() {
        List<Balance> balances = balanceService.getAllBalances();
        List<BalanceDTO> dtoList = balances.stream()
                .map(balance -> new BalanceDTO( balance.getBalance(), balance.getUser().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
    //------------------------------------------------------------------- 
    @GetMapping("/{id}")
    public ResponseEntity<BalanceDTO> getBalanceById(@PathVariable Long id) {
        Optional<Balance> balance = balanceService.getBalanceById(id);
        return balance.map(b -> ResponseEntity.ok(new BalanceDTO( b.getBalance(), b.getUser().getId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //------------------------------------------------------------------- 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable Long id) {
        if (balanceService.getBalanceById(id).isPresent()) {
            balanceService.deleteBalance(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //-------------------------------------------------------------------     
    @GetMapping("/{userId}/userbalance")
        public ResponseEntity<BigDecimal> getUserBalance(@PathVariable Long userId) {
        BigDecimal totalBalance = balanceService.calculateTotalBalanceForUser(userId);
        return ResponseEntity.ok(totalBalance);
    }

}
