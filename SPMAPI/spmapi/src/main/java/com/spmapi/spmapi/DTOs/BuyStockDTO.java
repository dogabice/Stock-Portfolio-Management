package com.spmapi.spmapi.DTOs;

import org.apache.catalina.User;

import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.Stock;
import com.spmapi.spmapi.model.Transaction;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuyStockDTO {

    //@ManyToOne
    //@JoinColumn(name = "portfolio_id")
    //private Portfolio portfolio;
    public Long portfolio_id;

    //@ManyToOne
    //@JoinColumn(name = "stock_id")
    //private Stock stock;
    public Long stock_id;

    //@ManyToOne
    //@JoinColumn(name = "user_id") // Kullanıcıyı tanımlamak için gerekli
    //private User user;
    public Long user_id;

    public int quantity;
    public BigDecimal price;
    public BigDecimal commission;

    /* 

    public BuyStockDTO transactionToBuyStockDTO(Transaction transaction){
        BuyStockDTO buyStockDTO = new BuyStockDTO();
        buyStockDTO.setPortfolio(portfolio_id);
        buyStockDTO.setStock(stock);
        buyStockDTO.setUser(user);
        buyStockDTO.setQuantity(transaction.getQuantity());
        buyStockDTO.setPrice(transaction.getPrice());
        buyStockDTO.setCommission(transaction.getCommission());
   
    return buyStockDTO;
   }
    */


}