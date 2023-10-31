package de.footystats.tools.controller;

import de.footystats.tools.services.ServiceException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Converts a service exception {@link ServiceException} into an {@link ExceptionResponse}. Doing it that way we do not take care of doing exception
 * handling inside controller methods that make use of service methods that may throw a {@link ServiceException}. that make use of service methods
 * that may throw a {@link ServiceException}.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)),
		responseCode = "409", description = "Some service could not finish normally.")
	@ExceptionHandler(value = {ServiceException.class})
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		ServiceException sexc = (ServiceException) ex;

		return handleExceptionInternal(ex, new ExceptionResponse(sexc.getType().name()), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
