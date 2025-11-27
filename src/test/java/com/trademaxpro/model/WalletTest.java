package com.trademaxpro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void testAddBalance() {
        Wallet wallet = new Wallet(1000);

        wallet.add(500);   // should become 1500

        assertEquals(1500.0, wallet.getBalance());
    }

    @Test
    void testDeductBalance() {
        Wallet wallet = new Wallet(1000);

        wallet.deduct(300); // should become 700

        assertEquals(700.0, wallet.getBalance());
    }

    @Test
    void testDeductThrowsException() {
        Wallet wallet = new Wallet(200);

        assertThrows(RuntimeException.class, () -> wallet.deduct(500));
    }

    @Test
    void testAddNegativeAmount() {
        Wallet wallet = new Wallet(500);

        assertThrows(RuntimeException.class, () -> wallet.add(-200));
    }
}
