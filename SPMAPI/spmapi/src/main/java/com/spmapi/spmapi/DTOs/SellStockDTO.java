package com.spmapi.spmapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellStockDTO {

    public Long portfolio_id;
    public Long stock_id;
    public Long user_id;

    public int quantity;
}