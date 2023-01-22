package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice
	extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {ServiceException.class})
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		ServiceException sexc = (ServiceException) ex;

		return handleExceptionInternal(ex, new ExceptionResponse(sexc.getType().name()),
			new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
