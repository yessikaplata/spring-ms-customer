package co.com.pragma.customer.servicecustomer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdentificationTypeDTO {
	private int id;
	private String abbreviation;
	private String name;
}
