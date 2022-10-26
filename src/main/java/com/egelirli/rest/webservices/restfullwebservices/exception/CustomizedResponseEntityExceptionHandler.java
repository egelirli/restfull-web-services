package com.egelirli.rest.webservices.restfullwebservices.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.egelirli.rest.webservices.restfullwebservices.user.UserNotFoundException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler 
								extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(
								Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(
									Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
		
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			                   MethodArgumentNotValidException ex, HttpHeaders headers, 
			                   HttpStatusCode status, WebRequest request) {

		StringBuffer errors = new StringBuffer();
		errors.append("Errors: ");
		List<FieldError>  errorList =  ex.getFieldErrors();
		int i = 0;
		for ( FieldError fieldError : errorList) {
			errors.append(++i + "-) ");
			errors.append(fieldError.getDefaultMessage());
			errors.append("  ");
			
		}
		
		ErrorDetails errorDetails = 
				    new ErrorDetails(LocalDateTime.now(), 
				                     errors.toString(), 
				                     request.getDescription(false));
		
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
		
	}
	
}
