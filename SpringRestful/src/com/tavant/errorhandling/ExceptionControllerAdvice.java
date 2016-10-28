package com.tavant.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorHandler> exceptionHandler(Exception ex) {
		ex.printStackTrace();
		ErrorHandler error = new ErrorHandler();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		error.setStackTrace(ex.getStackTrace());
		return new ResponseEntity<ErrorHandler>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}