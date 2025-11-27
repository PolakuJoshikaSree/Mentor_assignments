package com.trademaxpro.service;

import com.trademaxpro.exception.InsufficientFundsException;
import com.trademaxpro.model.User;
import com.trademaxpro.model.Wallet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletServiceTest {

    @Test
    void addMoney_increasesBalance() {
        User user = new User();
        user.setWallet(new Wallet(0));

        UserService userService = mock(UserService.class);
        when(userService.getUser("u1")).thenReturn(user);

        WalletService walletService = new WalletService(userService);

        double balance = walletService.addMoney("u1", 500.0);

        assertEquals(500.0, balance);
    }

    @Test
    void withdrawMoney_lowBalance_throws() {
        User user = new User();
        user.setWallet(new Wallet(100));

        UserService userService = mock(UserService.class);
        when(userService.getUser("u1")).thenReturn(user);

        WalletService walletService = new WalletService(userService);

        assertThrows(InsufficientFundsException.class,
                () -> walletService.withdrawMoney("u1", 200.0));
    }
}
