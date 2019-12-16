package com.invillia.acme.exceptions;

public class PaymentPendingException extends RuntimeException {

   
	private static final long serialVersionUID = 1L;

	public PaymentPendingException( ) {
        super("Payment is pending your ORDER");
    }
}
