package de.footystats.tools.services;

/**
 * This exception is thrown by services if something goes wrong. The exception contains a type that can be used to determine the cause of the
 * exception.
 */
public class ServiceException extends RuntimeException {

	private final Type type;

	public ServiceException(Type type) {
		this.type = type;
	}

	public ServiceException(Type type, Throwable t) {
		super(t);
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		ENCRYPTION_SERVICE_CREATION_FAILED(1),
		ENCRYPTION_SERVICE_ENCODING_FAILED(2),
		ENCRYPTION_SERVICE_DECODING_FAILED(3),
		CSV_FILE_DOWNLOAD_SERVICE_DL_FAILED(4),
		CSV_FILE_DOWNLOAD_SERVICE_SETTINGS_MISSING(5),
		CSV_FILE_DOWNLOAD_SERVICE_RETRIEVING_SESSION_FAILED(6),
		CSV_FILE_DOWNLOAD_SERVICE_SESSION_ID_MISSING(7),
		CSV_FILE_DOWNLOAD_SERVICE_LOGIN_FAILED(8),
		PREDICTION_QUALITY_SERVICE_RECOMPUTE_FAILED(9),
		CSV_FILE_SERVICE_IO_EXCEPTION(10), // Some io exception while importing csv file,
		CSV_FILE_SERVICE_LIB_EXCEPTION(11), // Excpetion produced by csv library while importing csv file.
		CSV_FILE_SERVICE_UNEXPECTED_EXCEPTION(12), // Some unexpected exception while importing csv file.
		CSV_FILE_SERVICE_NO_CSV_TYPE(13), // No csv type found for the given file name. Check the filename.
		CSV_FILE_SERVICE_NO_CSV_EXTENSION(14), // No csv extension found for the given file name. Check the filename.
		;
		final int code;

		Type(int code) {
			this.code = code;
		}
	}
}
