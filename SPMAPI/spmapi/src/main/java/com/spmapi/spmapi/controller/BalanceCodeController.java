package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.model.BalanceCode;
import com.spmapi.spmapi.service.BalanceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/balance-codes")
public class BalanceCodeController {

    @Autowired
    private BalanceCodeService balanceCodeService;

    @GetMapping
    public ResponseEntity<List<BalanceCode>> getAllBalanceCodes() {
        List<BalanceCode> balanceCodes = balanceCodeService.getAllBalanceCodes();
        return new ResponseEntity<>(balanceCodes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BalanceCode> createBalanceCode(@RequestBody BalanceCode balanceCode) {
        BalanceCode createdBalanceCode = balanceCodeService.saveBalanceCode(balanceCode);
        return new ResponseEntity<>(createdBalanceCode, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalanceCode(@PathVariable Long id) {
        balanceCodeService.deleteBalanceCode(id);
        return ResponseEntity.noContent().build();
    }
}