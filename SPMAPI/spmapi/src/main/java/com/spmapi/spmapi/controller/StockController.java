package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.BuyStockDTO;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.service.BuyStockService;
import com.spmapi.spmapi.service.StockService;
import com.spmapi.spmapi.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @Autowired
    private BuyStockService buyStockService;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("stocks", stockService.getAllStocks());
        return "home";
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/buy")
    public  Transaction buyStock(@RequestBody BuyStockDTO buyStockDTO) {
        Transaction transaction= buyStockService.BuyStockDTOToTransaction(buyStockDTO);
        return transactionService.saveTransaction(transaction);
    }




    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        if (stockService.getStockById(id).isPresent()) {
            stock.setId(id);
            return ResponseEntity.ok(stockService.saveStock(stock));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        if (stockService.getStockById(id).isPresent()) {
            stockService.deleteStock(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
