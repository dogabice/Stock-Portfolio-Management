package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.DTOs.CreateUserDTO;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.repository.UserRepository;
import com.spmapi.spmapi.repository.PortfolioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

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

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User assignRole(User user, String role) {
        user.setRole(role);
        return userRepository.save(user);
    }

    public User updateUserBalance(User user, double amount) {
        user.setBalance(amount);
        return userRepository.save(user);
    }
    //-------------------------------------------------------------
    public boolean addBalanceUsingCard(Long userId, String cardCode) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            double additionalBalance = 100.0;
            existingUser.setBalance(existingUser.getBalance() + additionalBalance);
            userRepository.save(existingUser);
            return true;
        }
        return false;
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
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUser(String key, User user) {
        redisTemplate.opsForValue().set(key, user);
    }
    //-------------------------------------------------------------
    public User getUser(String key) {
        return (User) redisTemplate.opsForValue().get(key);
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
}
