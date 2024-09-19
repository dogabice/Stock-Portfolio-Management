package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.model.BalanceCode;
import com.spmapi.spmapi.model.UserBalanceCode;
import com.spmapi.spmapi.repository.BalanceCodeRepository;
import com.spmapi.spmapi.repository.BalanceRepository;
import com.spmapi.spmapi.repository.UserBalanceCodeRepository;
import com.spmapi.spmapi.repository.UserRepository;
import com.spmapi.spmapi.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserBalanceCodeService {
    //---------------------------------------------------------------- 
    @Autowired
    private UserBalanceCodeRepository userBalanceCodeRepository;
    //---------------------------------------------------------------- 
    @Autowired
    private BalanceCodeRepository balanceCodeRepository;
    //---------------------------------------------------------------- 
    @Autowired
    private BalanceRepository balanceRepository;
    //---------------------------------------------------------------- 
    @Autowired
    private UserRepository userRepository;
    //---------------------------------------------------------------- 
    public List<UserBalanceCode> getAllUserBalanceCodes() {
        return userBalanceCodeRepository.findAll();
    }
    //---------------------------------------------------------------- 
    public UserBalanceCode saveUserBalanceCode(UserBalanceCode userBalanceCode) {
        return userBalanceCodeRepository.save(userBalanceCode);
    }
    //---------------------------------------------------------------- 
    public void deleteUserBalanceCode(Long id) {
        userBalanceCodeRepository.deleteById(id);
    }
    //---------------------------------------------------------------- 
    public List<UserBalanceCode> getUnusedBalanceCodesByUserId(Long userId) {
        return userBalanceCodeRepository.findByUserIdAndUsedFalse(userId);
    }
    //---------------------------------------------------------------- 
    public void markBalanceCodeAsUsed(UserBalanceCode userBalanceCode) {
        userBalanceCode.setUsed(true);
        userBalanceCodeRepository.save(userBalanceCode);
    }
    //---------------------------------------------------------------- 
    public boolean assignBalanceCodeToUser(Long userId, Long balanceCodeId) {
    Optional<User> userOptional = userRepository.findById(userId);
    Optional<BalanceCode> balanceCodeOptional = balanceCodeRepository.findById(balanceCodeId);

    if (userOptional.isPresent() && balanceCodeOptional.isPresent()) {
        User user = userOptional.get();
        BalanceCode balanceCode = balanceCodeOptional.get();

        BigDecimal currentBalance = user.getBalance();

        BigDecimal balanceCodeAmount = balanceCode.getAmount();

        //Add the BalanceCode's amount to the current balance
        BigDecimal newBalance = currentBalance.add(balanceCodeAmount);

        user.setBalance(newBalance);

        UserBalanceCode userBalanceCode = new UserBalanceCode();
        userBalanceCode.setUser(user);
        userBalanceCode.setBalanceCode(balanceCode);
        userBalanceCode.setUsed(true);  // marked

        userRepository.save(user);
        userBalanceCodeRepository.save(userBalanceCode);

        return true;  
    } else {
        return false;  
    }
}
    //---------------------------------------------------------------- 
    public void deductFromUserBalance(User user, BigDecimal amount) {
        // Kullanıcının mevcut bakiyesini al
        Optional<Balance> optionalBalance = balanceRepository.findByUser(user);
        
        BigDecimal currentBalance = optionalBalance
            .map(Balance::getBalance)
            .orElse(BigDecimal.ZERO);
        
        // Yeni bakiyeyi hesapla
        BigDecimal newBalance = currentBalance.subtract(amount);
        
        // Bakiyeyi güncelle
        Balance balance = optionalBalance.orElse(new Balance(user, BigDecimal.ZERO));
        balance.setBalance(newBalance);
        balanceRepository.save(balance);
    }
    
    public void addToUserBalance(User user, BigDecimal amount) {
        // Kullanıcının mevcut bakiyesini al
        Optional<Balance> optionalBalance = balanceRepository.findByUser(user);
        
        BigDecimal currentBalance = optionalBalance
            .map(Balance::getBalance)
            .orElse(BigDecimal.ZERO);
        
        // Yeni bakiyeyi hesapla
        BigDecimal newBalance = currentBalance.add(amount);
        
        // Bakiyeyi güncelle
        Balance balance = optionalBalance.orElse(new Balance(user, BigDecimal.ZERO));
        balance.setBalance(newBalance);
        balanceRepository.save(balance);
    }
    
    
}

