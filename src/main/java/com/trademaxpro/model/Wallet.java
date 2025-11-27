// Wallet.java
package com.trademaxpro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// simple wallet for money
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    private double balance = 0.0;   // default balance

    public void add(double amount) {
        balance = balance + amount; // add money
    }

    public void deduct(double amount) {
        balance = balance - amount; // remove money
    }
}
