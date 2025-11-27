package com.trademaxpro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// each stock owned by user
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItem {

    private String ticker;    // e.g. TCS
    private int quantity;     // how many shares
    private double avgPrice;  // average buy price

    public void addMore(int qty, double price) {
        double totalOld = avgPrice * quantity;
        double totalNew = price * qty;
        int newQty = quantity + qty;

        avgPrice = (totalOld + totalNew) / newQty; // new avg
        quantity = newQty;
    }

    public void sell(int qty) {
        quantity = quantity - qty; // reduce quantity
    }
}
