package com.tavant.errorhandling;

public class ErrorHandler extends Exception {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String message;
	private StackTraceElement[] stackTraceElement;
	
	public ErrorHandler(String message) {
		super(message);
		this.message = message;
	}
	
	public ErrorHandler() {
	}

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public StackTraceElement[] getStackTraceElement() {
		return stackTraceElement;
	}

	public void setStackTraceElement(StackTraceElement[] stackTraceElement) {
		this.stackTraceElement = stackTraceElement;
	}
	
}