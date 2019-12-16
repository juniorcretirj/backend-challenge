package com.invillia.acme.exceptions;

public class RefundExpiredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    public RefundExpiredException(int EXPIRED) {
        super("Refund deadline expired "+EXPIRED+" days");
    }
}
