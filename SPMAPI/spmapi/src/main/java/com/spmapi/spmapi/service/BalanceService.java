package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.model.UserBalanceCode;
import com.spmapi.spmapi.repository.BalanceRepository;
import com.spmapi.spmapi.repository.UserBalanceCodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {
    //----------------------------------------------------------------     
    @Autowired
    private BalanceRepository balanceRepository;
    //----------------------------------------------------------------     
    @Autowired
    private UserBalanceCodeRepository userBalanceCodeRepository;
    //---------------------------------------------------------------- 
    public List<Balance> getAllBalances() {
        return balanceRepository.findAll();
    }
    //---------------------------------------------------------------- 
    public Optional<Balance> getBalanceById(Long id) {
        return balanceRepository.findById(id);
    }
    //---------------------------------------------------------------- 
    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }
    //---------------------------------------------------------------- 
    public void deleteBalance(Long id) {
        balanceRepository.deleteById(id);
    }
    //----------------------------------------------------------------     
    public Optional<Balance> getBalanceByUserId(Long userId) {
        return balanceRepository.findByUserId(userId);
    }
    //----------------------------------------------------------------     
    public BigDecimal calculateTotalBalanceForUser(Long userId) {
        BigDecimal totalBalance = BigDecimal.ZERO;
        // Take the balance codes that assigned to user
        List<UserBalanceCode> userBalanceCodes = userBalanceCodeRepository.findByUserId(userId);
        
        for (UserBalanceCode balanceCode : userBalanceCodes) {
            totalBalance = totalBalance.add(balanceCode.getBalanceCode().getAmount());
        }
        // Take user's balance 
        Optional<Balance> userBalance = balanceRepository.findByUserId(userId);
        
        if (userBalance.isPresent()) {
            Balance existingBalance = userBalance.get();
            // Compare current balance with total balance
            if (!existingBalance.getBalance().equals(totalBalance)) {
                existingBalance.setBalance(totalBalance);  // New balance value
                balanceRepository.save(existingBalance);   // Update
            }
        } else {
            // If the user does not have a balance, create a new one
            User user = new User();
            user.setId(userId);  
            
            Balance newBalance = new Balance();
            newBalance.setUser(user); 
            newBalance.setBalance(totalBalance);
            balanceRepository.save(newBalance); 
        }
    
        return totalBalance; 
    }
    
    
    

}
