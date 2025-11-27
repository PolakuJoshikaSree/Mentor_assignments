package com.trademaxpro.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Wallet {

    private double balance;

    public void add(double amount) {
        if (amount <= 0)
            throw new RuntimeException("Amount must be positive");
        this.balance += amount;
    }

    public void deduct(double amount) {
        if (amount > balance)
            throw new RuntimeException("Insufficient balance");
        this.balance -= amount;
    }
}
