package com.rstech.wordwatch.web;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;

public class ApplicationError {
	private static final Class thisClass = ApplicationError.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	
	static final String ERROR_POSITIVE = "Y";
	static final String ERROR_NEGATIVE = "N";
	
	
	String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	String errorText;
	String errorCode;
	
	public ApplicationError()
	{
		this.setErrorIndicator(ERROR_NEGATIVE);
	}
	
	public ApplicationError(String errorCode, String errorText, String msg) {
		String methodName = "ApplicationError";
		logger.debug("entering " + methodName);
		
		this.setErrorIndicator(ERROR_POSITIVE);
		this.setErrorCode(errorCode);
		this.setErrorText(errorText);
		this.setMessage(msg);
		logger.debug("exiting " + methodName);
	}
	
	public String getErrorIndicator() {
		System.out.println("Getting error indicator: value=" + errorIndicator);
		return errorIndicator;
	}
	public void setErrorIndicator(String errorIndicator) {
		this.errorIndicator = errorIndicator;
	}
	public ApplicationError(int errorCode, String errorText, String msg) {
		this(Integer.valueOf(errorCode).toString(), errorText, msg); 
	}
	
	String errorIndicator;

}
