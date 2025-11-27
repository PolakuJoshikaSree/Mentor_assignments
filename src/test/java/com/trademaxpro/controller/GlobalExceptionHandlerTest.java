package com.trademaxpro.controller;

import com.trademaxpro.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void userNotFoundHandled() {
        ResponseEntity<String> response = handler.handleUser(new UserNotFoundException("User missing"));
        assertEquals(404, response.getStatusCode().value());
        assertEquals("User missing", response.getBody());
    }

    @Test
    void tradeRelatedErrorsHandled() {
        ResponseEntity<String> response =
                handler.handleTrade(new InvalidQuantityException("Invalid qty"));
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid qty", response.getBody());
    }

    @Test
    void fallbackExceptionHandled() {
        ResponseEntity<String> response =
                handler.handleOther(new Exception("X failed"));

        assertEquals(500, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Something went wrong"));
    }
}
