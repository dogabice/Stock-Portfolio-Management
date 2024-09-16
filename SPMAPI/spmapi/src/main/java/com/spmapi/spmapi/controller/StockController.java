package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.BuyStockDTO;
import com.spmapi.spmapi.DTOs.SellStockDTO;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;
import com.spmapi.spmapi.service.BuyStockService;
import com.spmapi.spmapi.service.SellStockService;
import com.spmapi.spmapi.service.StockService;
import com.spmapi.spmapi.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @Autowired
    private BuyStockService buyStockService;

    @Autowired
    private SellStockService sellStockService;

    @Autowired
    private TransactionService transactionService;

    //-------------------------------------------------------------------
    //STOCK TRANSACTIONS

    @PostMapping("/buy")
    public  Transaction buyStock(@RequestBody BuyStockDTO buyStockDTO) {
        Transaction transaction= buyStockService.BuyStockDTOToTransaction(buyStockDTO);
        return transactionService.saveTransaction(transaction);
    }

    @PostMapping("/sell")
    public  Transaction sellStock(@RequestBody SellStockDTO sellStockDTO) {
        Transaction transaction= sellStockService.SellStockDTOToTransaction(sellStockDTO);
        return transactionService.saveTransaction(transaction);
    }

    //-------------------------------------------------------------------
   
}
