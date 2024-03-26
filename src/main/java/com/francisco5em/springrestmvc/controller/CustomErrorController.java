/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Creado por Francisco E.
 */
@Slf4j
@ControllerAdvice
public class CustomErrorController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity handleBindErrors(MethodArgumentNotValidException excep) {
		log.info("handleBindErrors - MethodArgumentNotValidException - info:"
				+ excep.getMessage() + "\n\n");
		List errors = excep.getFieldErrors().stream().map(fieldError -> {
			Map<String, String> errormap = new HashMap<>();
			errormap.put(fieldError.getField(), fieldError.getDefaultMessage());
			return errormap;
		}).collect(Collectors.toList());

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler
	ResponseEntity handleJPAViolations(TransactionSystemException excep) {
		log.info("handleJPAViolations - TransactionSystemException - info:"
				+ excep.getMessage());
		ResponseEntity.BodyBuilder response = ResponseEntity.badRequest();

		if (excep.getCause().getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException cVE = (ConstraintViolationException) excep
					.getCause().getCause();
			List errors = cVE.getConstraintViolations().stream()
					.map(cons->{
						 Map<String,String> eMap= new HashMap<String, String>();
						 eMap.put(cons.getPropertyPath().toString(), cons.getMessage());
						return eMap;
					}).collect(Collectors.toList());
			return response.body(errors);
		}

		return response.build();

	}

}
