package co.com.pragma.customer.servicecustomer.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

	@NotNull(message = "Identification Type must not be null.")
	private IdentificationTypeDTO identificationType;

	@NotEmpty(message = "Identification must not be empty.")
	private String identification;

	@NotEmpty(message = "Name must not be empty.")
	private String name;

	@NotEmpty(message = "Lastname must not be empty.")
	private String lastName;

	@NotNull(message = "Age must not be null.")
	private Integer age;

	@NotNull(message = "City of birth must not be null.")
	private CityDTO cityOfBirth;

	private PhotoDTO photo;
	
}
