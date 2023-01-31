package com.request.api.exception;

public class PurchaseNotExistsException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public PurchaseNotExistsException(String message){
        super(message);
    }

}