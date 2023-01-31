package com.request.api.exception;

public class ProductNotExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ProductNotExistsException(String message){
        super(message);
    }

}