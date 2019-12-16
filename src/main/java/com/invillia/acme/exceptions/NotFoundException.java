package com.invillia.acme.exceptions;

public class NotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	  public NotFoundException(String value) {
		    super("Not Found " + value);
		  }
}
