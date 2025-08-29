package de.footystats.tools;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public class FootystatsRuntimeException extends RuntimeException {

	private final Collection<ExceptionDetail> details;

	public FootystatsRuntimeException(ExceptionDetail detail) {
		details = List.of(detail);
	}

	public FootystatsRuntimeException(Collection<ExceptionDetail> detail) {
		details = List.copyOf(detail);
	}


	public enum Type {
		UNSPECIFIED,
		EXCEPTION,
		INVALID_INPUT,
		DATA_INTEGRITY,
	}

	public record ExceptionDetail(Type type, Class<?> source, String dataObjectName, String message) {
	}
}
