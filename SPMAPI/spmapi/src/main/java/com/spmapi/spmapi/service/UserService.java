package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.PortfolioStock;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.repository.UserRepository;
import com.spmapi.spmapi.repository.PortfolioRepository;
import com.spmapi.spmapi.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockRepository stockRepository;


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

    public void createPortfolioForUser(User user) {
        // Kullanıcının zaten bir portföyü olup olmadığını kontrol et
        List<Portfolio> existingPortfolios = portfolioRepository.findByUser(user);
        if (existingPortfolios.isEmpty()) {
            Portfolio newPortfolio = new Portfolio();
            newPortfolio.setUser(user);
    
            List<Long> defaultStockIds = List.of(1L, 2L, 3L); // Varsayılan stok ID'leri
    
            List<PortfolioStock> defaultStocks = new ArrayList<>();
            for (Long stockId : defaultStockIds) {
                Optional<Stock> stock = stockRepository.findById(stockId);
                if (stock.isPresent()) {
                    PortfolioStock portfolioStock = new PortfolioStock();
                    portfolioStock.setPortfolio(newPortfolio);
                    portfolioStock.setStock(stock.get());
                    portfolioStock.setQuantity(0); // Başlangıç miktarı
                    defaultStocks.add(portfolioStock);
                } else {
                    System.err.println("Stok ID'si " + stockId + " bulunamadı.");
                }
            }
            newPortfolio.setPortfolioStocks(defaultStocks);
            portfolioRepository.save(newPortfolio); // Portföyü kaydet
        }
    }
    
    

    public List<User> getUsersByRole(String roleName) {
        return userRepository.findUsersByRole(roleName);
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUser(String key, User user) {
        redisTemplate.opsForValue().set(key, user);
    }

    public User getUser(String key) {
        return (User) redisTemplate.opsForValue().get(key);
    }

    public long getUserCount() {
        return userRepository.count();   
    }

    // Kullanıcıyı ve portföyü oluşturur
    public User createUser(User user) {
        User savedUser = saveUser(user);
        createPortfolioForUser(savedUser); // Kullanıcıyı oluşturduktan sonra portföy oluştur
        return savedUser;
    }
    
    
}
