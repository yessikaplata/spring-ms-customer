package co.com.pragma.customer.servicecustomer.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ServiceCustomerException extends RuntimeException {

	private HttpStatus httpStatus;

	public ServiceCustomerException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}

}
