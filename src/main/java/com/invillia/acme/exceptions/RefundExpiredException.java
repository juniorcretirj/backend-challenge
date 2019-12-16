package com.invillia.acme.exceptions;

public class RefundExpiredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    public RefundExpiredException(int eXPIRED) {
        super("Refund deadline expired "+eXPIRED+" days");
    }
}
