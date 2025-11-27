package com.trademaxpro.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionTests {

    @Test void testInsufficientHoldings() {
        InsufficientHoldingsException ex = new InsufficientHoldingsException("Not enough");
        assertEquals("Not enough", ex.getMessage());
    }

    @Test void testInvalidQuantity() {
        InvalidQuantityException ex = new InvalidQuantityException("Bad qty");
        assertEquals("Bad qty", ex.getMessage());
    }

    @Test void testUserNotFound() {
        UserNotFoundException ex = new UserNotFoundException("Missing user");
        assertEquals("Missing user", ex.getMessage());
    }

    @Test void testInsufficientFunds() {
        InsufficientFundsException ex = new InsufficientFundsException("Funds low");
        assertEquals("Funds low", ex.getMessage());
    }
}
