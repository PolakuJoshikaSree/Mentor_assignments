package com.trademaxpro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void testAddBalance() {
        Wallet wallet = new Wallet(1000);
        wallet.setBalance(500);
        assertEquals(1500, wallet.getBalance());
    }

    @Test
    void testDeductBalance() {
        Wallet wallet = new Wallet(1000);
        wallet.setBalance(300);
        assertEquals(700, wallet.getBalance());
    }

    @Test
    void testDeductThrowsException() {
        Wallet wallet = new Wallet(200);
        assertThrows(RuntimeException.class, () -> wallet.setBalance(500));
    }
}
