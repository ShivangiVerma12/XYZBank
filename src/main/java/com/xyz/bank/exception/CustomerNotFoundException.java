package com.xyz.bank.exception;

public class CustomerNotFoundException extends Exception{
	public static final String message = "customer not found for id ";
    public CustomerNotFoundException(long id){
        super(message+id);
    }

}
