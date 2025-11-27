package com.trademaxpro.model;

import lombok.Data;
import lombok.AllArgsConstructor;

// view details for each stock when showing portfolio.
@Data
@AllArgsConstructor
public class PortfolioViewItem {

    private String ticker;          
    private int quantity;           
    private double avgBuyPrice;     
    private double currentPrice;    
    private double investedValue;   // avg * qty
    private double currentValue;    // current * qty
    private double profitLossPercent; 
}
