package com.spmapi.spmapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuyStockDTO {

    public Long portfolio_id;
    public Long stock_id;
    public Long user_id;

    public int quantity;
    //public BigDecimal price;
    //public BigDecimal commission;
}