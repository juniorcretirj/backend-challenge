package com.invillia.acme.domain.entity;

public enum StatusPayment {
    PENDING ("Pending"), 
    REFUND ("Refund"), 
    COMPLETED ("Completed");
    

	StatusPayment(String name){
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
