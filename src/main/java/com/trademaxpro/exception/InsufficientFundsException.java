package com.trademaxpro.exception;

// when wallet has less money than needed.
public class InsufficientFundsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
        super(message);
    }
}
