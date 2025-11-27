package com.trademaxpro.service;

import com.trademaxpro.exception.InsufficientFundsException;
import com.trademaxpro.model.User;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final UserService userService;

    public WalletService(UserService userService) {
        this.userService = userService;
    }

    public double addMoney(String userId, double amount) {
        User user = userService.getUser(userId);
        user.getWallet().add(amount);

        userService.updateUser(user);  
        return user.getWallet().getBalance();
    }

    public double withdrawMoney(String userId, double amount) {
        User user = userService.getUser(userId);

        if (user.getWallet().getBalance() < amount) {
            throw new InsufficientFundsException("Not enough balance in wallet");
        }

        user.getWallet().deduct(amount);

        userService.updateUser(user); 
        return user.getWallet().getBalance();
    }

    public double viewBalance(String userId) {
        return userService.getUser(userId).getWallet().getBalance();
    }
}
