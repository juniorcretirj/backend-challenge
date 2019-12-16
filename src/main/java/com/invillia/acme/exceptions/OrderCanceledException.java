package com.invillia.acme.exceptions;

public class OrderCanceledException extends RuntimeException {

    
	private static final long serialVersionUID = 1L;

	public OrderCanceledException() {
        super("Order canceled");
    }
}
