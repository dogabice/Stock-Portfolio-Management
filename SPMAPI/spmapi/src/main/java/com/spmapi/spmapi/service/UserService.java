package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.model.UserBalanceCode;
import com.spmapi.spmapi.DTOs.CreateUserDTO;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.repository.UserRepository;
import com.spmapi.spmapi.repository.PortfolioRepository;
import com.spmapi.spmapi.repository.UserBalanceCodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //---------------------------------------------------------------- 
    @Autowired
    private UserRepository userRepository;
    //---------------------------------------------------------------- 
    @Autowired
    private PortfolioRepository portfolioRepository;
    //-------------------------------------------------------------
    @Autowired
    private UserBalanceCodeRepository userBalanceCodeRepository;
    //-------------------------------------------------------------
    public User CreateUserDTOToUser(CreateUserDTO createUserDTO){
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());
   
    return user;
   }
   //-------------------------------------------------------------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    //---------------------------------------------------------------- 
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    //---------------------------------------------------------------- 
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    //---------------------------------------------------------------- 
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    //---------------------------------------------------------------- 
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    //---------------------------------------------------------------- 
    public User assignRole(User user, String role) {
        user.setRole(role);
        return userRepository.save(user);
    }
    //---------------------------------------------------------------- 
    public User updateUserBalance(User user, BigDecimal amount) {
        user.setBalance(amount);
        return userRepository.save(user);
    }
    //-------------------------------------------------------------
    public void createPortfolioForUser(User user) {
        List<Portfolio> existingPortfolios = portfolioRepository.findByUser(user);
        if (existingPortfolios.isEmpty()) {
            Portfolio newPortfolio = new Portfolio();
            newPortfolio.setUser(user);
            portfolioRepository.save(newPortfolio); 
        }
    }
    //-------------------------------------------------------------
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findUsersByRole(roleName);
    }
    //-------------------------------------------------------------
    public long getUserCount() {
        return userRepository.count();   
    }
    //-------------------------------------------------------------
    public User createUser(User user) {
        User savedUser = saveUser(user);
        createPortfolioForUser(savedUser); 
        return savedUser;
    }
    //-------------------------------------------------------------
    public BigDecimal calculateTotalBalanceCodesAmount(Long userId) {
        List<UserBalanceCode> userBalanceCodes = userBalanceCodeRepository.findByUserId(userId);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (UserBalanceCode userBalanceCode : userBalanceCodes) {
            totalAmount = totalAmount.add(userBalanceCode.getBalanceCode().getAmount());
        }

        return totalAmount;
    }
    //-------------------------------------------------------------
    public void updateUserBalanceFromBalanceCodes(Long userId) {
        BigDecimal totalAmount = calculateTotalBalanceCodesAmount(userId);
        Optional<User> userOptional = userRepository.findById(userId);
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBalance(totalAmount); 
            userRepository.save(user);
        }
    }
}
