package com.request.api.exception;

public class AdminNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AdminNotExistException(String message){
        super(message);
    }

}