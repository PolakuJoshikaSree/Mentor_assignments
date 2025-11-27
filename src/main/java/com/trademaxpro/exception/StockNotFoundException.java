package com.trademaxpro.exception;

// when ticker is not in dummy api.
public class StockNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public StockNotFoundException(String message) {
        super(message);
    }
}
