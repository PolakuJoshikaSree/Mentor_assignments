package com.trademaxpro.exception;

// when qty <= 0
public class InvalidQuantityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public InvalidQuantityException(String message) {
        super(message);
    }
}
