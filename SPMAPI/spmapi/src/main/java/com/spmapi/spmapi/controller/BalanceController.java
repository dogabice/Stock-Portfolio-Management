package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {
    
    @Autowired
    private BalanceService balanceService;

    @GetMapping
    public ResponseEntity<List<Balance>> getAllBalances() {
        List<Balance> balances = balanceService.getAllBalances();
        return ResponseEntity.ok(balances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Balance> getBalanceById(@PathVariable Long id) {
        Optional<Balance> balance = balanceService.getBalanceById(id);
        return balance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Balance> createBalance(@RequestBody Balance balance) {
        Balance createdBalance = balanceService.saveBalance(balance);
        return ResponseEntity.ok(createdBalance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Balance> updateBalance(@PathVariable Long id, @RequestBody Balance balance) {
        if (balanceService.getBalanceById(id).isPresent()) {
            balance.setId(id);
            Balance updatedBalance = balanceService.saveBalance(balance);
            return ResponseEntity.ok(updatedBalance);
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
public ResponseEntity<Balance> getUserBalance(@PathVariable Long id) {
    Optional<Balance> balance = balanceService.getBalanceByUserId(id);
    return balance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
}


}
