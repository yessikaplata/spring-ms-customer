package co.com.pragma.customer.servicecustomer.controller;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
	
	private Date created;
	
	private int status;
	
	private String error;
	
	private String message;

	private String exception;
	
	private String path;
	

}
