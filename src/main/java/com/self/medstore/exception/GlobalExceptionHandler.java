package com.self.medstore.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//getClass().getName() for getting exact error
@ControllerAdvice
public class GlobalExceptionHandler {

	//1.
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "resource not found",
				ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	//2.
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Duplicate not allowed",
				ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.CONFLICT);
	}

	//3.
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage());
		//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
	}

	//4.
	@ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<ErrorResponse> handleOutOfStock(OutOfStockException ex) {

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Out of Stock",
				ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	//5.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.BAD_REQUEST.value());
		error.put("error", "Validation Failed");

		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));
		error.put("messages", fieldErrors);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	//6.
	@ExceptionHandler(OrderNotBelongToCustomerException.class)
	public ResponseEntity<ErrorResponse> handleOrderNotBelong(OrderNotBelongToCustomerException ex) {

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				"order is not yours", ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}
	
	//7.
	@ExceptionHandler(OrderShippedException.class)
	public ResponseEntity<ErrorResponse> handleOrderShipped(OrderShippedException ex) {

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				"trying to delete a shipped order", ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.CONFLICT);
	}

	//8.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Unexpected Error", ex.getMessage());
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

//2.
/*package com.self.medstore.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage());
	}

	@ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<Object> handleOutOfStock(OutOfStockException ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, "Out of Stock", ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneric(Exception ex) {
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", ex.getMessage());
	}
	
		@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.BAD_REQUEST.value());
		error.put("error", "Validation Failed");

		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));
		error.put("messages", fieldErrors);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

//		BindingResult bResult = ex.getBindingResult();
//		List<FieldError> errorList = bResult.getFieldErrors();
//		
//		for (FieldError error : errorList) {
//			errors.put(error.getField(), error.getDefaultMessage());
//		}
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String error, String message) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", error);
		body.put("message", message);
		return new ResponseEntity<>(body, status);
	}
}*/
