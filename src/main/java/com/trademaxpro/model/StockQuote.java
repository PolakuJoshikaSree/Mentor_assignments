package com.trademaxpro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// stock info from dummy api.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockQuote {

    private String ticker;   // code
    private String company;  
    private double price;    // current 
}
