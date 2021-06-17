package co.com.pragma.servicephoto.controller;

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

	private String message;

	private String nameException;
	
	private String uriRequest;
	
	private int statusCode;

}
