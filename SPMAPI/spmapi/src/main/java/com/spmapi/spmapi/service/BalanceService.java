package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    public List<Balance> getAllBalances() {
        return balanceRepository.findAll();
    }

    public Optional<Balance> getBalanceById(Long id) {
        
        return balanceRepository.findById(id);
    }

    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    public void deleteBalance(Long id) {
        balanceRepository.deleteById(id);
    }
    
    public Optional<Balance> getBalanceByUserId(Long userId) {
        return balanceRepository.findByUserId(userId);
    }
    
}
