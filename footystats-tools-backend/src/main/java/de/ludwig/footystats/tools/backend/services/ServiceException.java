package de.ludwig.footystats.tools.backend.services;

/**
 *
 */
public class ServiceException extends RuntimeException{

	private final Type type;

	public ServiceException(Type type){
		this.type = type;
	}

	public ServiceException(Type type, Throwable t){
		super(t);
		this.type = type;
	}


	public static enum Type {
		ENCRYPTION_SERVICE_CREATION_FAILED(1),
		ENCRYPTION_SERVICE_ENCODING_FAILED(2),
		ENCRYPTION_SERVICE_DECODING_FAILED(3),
		CSV_FILE_DOWNLOAD_SERVICE_DL_FAILED(4),
		CSV_FILE_DOWNLOAD_SERVICE_SETTINGS_MISSING(5),
		CSV_FILE_DOWNLOAD_SERVICE_RETRIEVING_SESSION_FAILED(6),
		CSV_FILE_DOWNLOAD_SERVICE_SESSION_ID_MISSING(7),
		;
		int code;

		Type(int code) {
			this.code = code;
		}
	}
}
