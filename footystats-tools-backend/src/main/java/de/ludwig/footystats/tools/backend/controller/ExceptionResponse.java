package de.ludwig.footystats.tools.backend.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

	public final String EXCEPTION_RESPONSE = "exceptionResponse";

	@Getter
	@Setter
	private String id;
}
