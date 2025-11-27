package com.trademaxpro.exception;

// when user sells more shares than owned
public class InsufficientHoldingsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public InsufficientHoldingsException(String message) {
        super(message);
    }
}
