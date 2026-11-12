package com.threedlinkweb.petclinic.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Manejo de errores de validación (400 Bad Request)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
			HttpStatus.BAD_REQUEST,
			"Error de validación en los datos enviados"
		);
		problemDetail.setTitle("Validación fallida");

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
			errors.put(error.getField(), error.getDefaultMessage())
		);
		problemDetail.setProperty("errors", errors);
		return problemDetail;
	}

	// Manejo de recurso no encontrado (404 Not Found)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
			HttpStatus.NOT_FOUND,
			ex.getMessage()
		);
		problemDetail.setTitle("Recurso no encontrado");
		return problemDetail;
	}
}
