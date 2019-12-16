package com.invillia.acme.domain.entity;

public enum StatusOrder {
    PAYMENT_PENDING ("Payment Pending"),
    PAYMENT_ACCEPT("Payment Accept"),
    TRANSPORT("In Transport"),
    REFUNDED("Refunded");
   

    StatusOrder(String name){
    	this.name=name;
    	
    }
    
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
    
}


