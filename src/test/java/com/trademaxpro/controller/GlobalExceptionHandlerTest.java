package com.trademaxpro.controller;

import com.trademaxpro.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleInvalidQuantity() {
        InvalidQuantityException ex = new InvalidQuantityException("Invalid qty");
        
        ResponseEntity<String> response = handler.handleInvalidQuantity(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Invalid qty");
    }

    @Test
    void testHandleTrade_StockNotFound() {
        StockNotFoundException ex = new StockNotFoundException("Stock missing");

        ResponseEntity<String> response = handler.handleTrade(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Stock missing");
    }

    @Test
    void testHandleTrade_InsufficientFunds() {
        InsufficientFundsException ex = new InsufficientFundsException("Low balance");

        ResponseEntity<String> response = handler.handleTrade(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Low balance");
    }

    @Test
    void testHandleTrade_InsufficientHoldings() {
        InsufficientHoldingsException ex = new InsufficientHoldingsException("No holdings");

        ResponseEntity<String> response = handler.handleTrade(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("No holdings");
    }

    @Test
    void testHandleOther() {
        Exception ex = new Exception("System crash");

        ResponseEntity<String> response = handler.handleOther(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("System crash");
    }
}
