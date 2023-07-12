package com.isteer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResponse {

	@ExceptionHandler(value = { ImageNameNotFoundException.class })
	public ResponseEntity<ExceptionHandlers> imageNameNotFound(ImageNameNotFoundException exceptions) {
		return new ResponseEntity<ExceptionHandlers>(new ExceptionHandlers(exceptions.getStatusCode(), exceptions.getMsg(), exceptions.getErrorMsg()),HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<ExceptionHandlers> commonException(Exception exceptions) {
		return new ResponseEntity<ExceptionHandlers>(
				new ExceptionHandlers(0, "data fetching failed", exceptions.getMessage()),
				HttpStatus.BAD_REQUEST);

	}

}
