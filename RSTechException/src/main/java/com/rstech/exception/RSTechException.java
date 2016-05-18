package com.rstech.exception;

public class RSTechException extends Throwable {
	String message;
	
	public void setMessage(String aMessage) { message = aMessage; }
	public String getMessage() { return message; }
}
