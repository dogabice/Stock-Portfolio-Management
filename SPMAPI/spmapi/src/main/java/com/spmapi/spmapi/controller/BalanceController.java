package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.BalanceDTO;
import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/balances")
public class BalanceController {
    
    @Autowired
    private BalanceService balanceService;

    @GetMapping
    public ResponseEntity<List<BalanceDTO>> getAllBalances() {
        List<Balance> balances = balanceService.getAllBalances();
        List<BalanceDTO> dtoList = balances.stream()
                .map(balance -> new BalanceDTO(balance.getId(), balance.getBalance(), balance.getUser().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BalanceDTO> getBalanceById(@PathVariable Long id) {
        Optional<Balance> balance = balanceService.getBalanceById(id);
        return balance.map(b -> ResponseEntity.ok(new BalanceDTO(b.getId(), b.getBalance(), b.getUser().getId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BalanceDTO> createBalance(@RequestBody BalanceDTO balanceDTO) {
        Balance balance = new Balance();
        balance.setBalance(balanceDTO.getBalance());
        balance.setUser(new User(balanceDTO.getUserId())); // User objesini uygun şekilde oluşturmalısınız
        Balance createdBalance = balanceService.saveBalance(balance);
        return ResponseEntity.ok(new BalanceDTO(createdBalance.getId(), createdBalance.getBalance(), createdBalance.getUser().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BalanceDTO> updateBalance(@PathVariable Long id, @RequestBody BalanceDTO balanceDTO) {
        if (balanceService.getBalanceById(id).isPresent()) {
            Balance balance = new Balance();
            balance.setId(id);
            balance.setBalance(balanceDTO.getBalance());
            balance.setUser(new User(balanceDTO.getUserId())); // User objesini uygun şekilde oluşturmalısınız
            Balance updatedBalance = balanceService.saveBalance(balance);
            return ResponseEntity.ok(new BalanceDTO(updatedBalance.getId(), updatedBalance.getBalance(), updatedBalance.getUser().getId()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable Long id) {
        if (balanceService.getBalanceById(id).isPresent()) {
            balanceService.deleteBalance(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/api/users/{id}/balance")
    public ResponseEntity<BalanceDTO> getUserBalance(@PathVariable Long id) {
        Optional<Balance> balance = balanceService.getBalanceByUserId(id);
        return balance.map(b -> ResponseEntity.ok(new BalanceDTO(b.getId(), b.getBalance(), b.getUser().getId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
