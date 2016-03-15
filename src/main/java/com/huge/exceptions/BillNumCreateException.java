package com.huge.exceptions;

public class BillNumCreateException extends ArithmeticException {
	
	private String message;

	public BillNumCreateException(){
		
	}
	public BillNumCreateException(String message){
		this.message = message;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
