package co.com.pragma.servicephoto.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ServicePhotoException extends RuntimeException {

	private HttpStatus httpStatus;

	public ServicePhotoException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}

}
