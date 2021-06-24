package co.com.pragma.customer.servicecustomer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

	private static final ConcurrentHashMap<String, Integer> STATUS_CODES = new ConcurrentHashMap<>();

	public ErrorHandler() {
		STATUS_CODES.put(MethodArgumentNotValidException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
		STATUS_CODES.put(MethodArgumentTypeMismatchException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());

	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> handleAllExceptions(HttpServletRequest request, Exception exception) {
		ResponseEntity<ErrorMessage> resultado;

		String excepcionNombre = exception.getClass().getSimpleName();
		String mensaje = formatMessage(exception);
		Integer codigo = getStatusCode(exception);

		if (codigo == null) {
			codigo = HttpStatus.INTERNAL_SERVER_ERROR.value();
		}

		ErrorMessage error = ErrorMessage.builder().message(mensaje).exception(excepcionNombre).created(new Date())
				.path(request.getRequestURI()).status(codigo).error(HttpStatus.valueOf(codigo).name()).build();
		resultado = new ResponseEntity<>(error, HttpStatus.valueOf(codigo));
		log.error(exception.getMessage());
		exception.printStackTrace();
		return resultado;
	}

	private Integer getStatusCode(Exception e) {
		if (e instanceof ServiceCustomerException) {
			ServiceCustomerException ex = (ServiceCustomerException) e;
			if (ex.getHttpStatus() != null) {
				return ex.getHttpStatus().value();
			}
		}
		return STATUS_CODES.get(e.getClass().getSimpleName());
	}

	private String formatMessage(Exception e) {
		if (e instanceof BindingResult) {
			BindingResult bindingResult = (BindingResult) e;
			return formatMessage(bindingResult);
		}
		if (e instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) e;
			String name = ex.getName();
			String type = ex.getRequiredType().getSimpleName();
			Object value = ex.getValue();
			String message = String.format("%s should be a valid %s and value '%s' is not.", name, type, value);
			return message;
		}
		return e.getMessage();
	};

	private String formatMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<String, String>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(errors);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return jsonString;
	}

}
