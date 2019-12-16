package com.invillia.acme.exceptions;

public class PaymentRequiredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public PaymentRequiredException() {
        super("Payment required");
    }
}
