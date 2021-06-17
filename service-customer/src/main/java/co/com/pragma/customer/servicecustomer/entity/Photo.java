package co.com.pragma.customer.servicecustomer.entity;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "photos")
public class Photo {

	@Id
	public BigInteger id;

	@Field(name = "customer_id")
	private Long customerId;

	private String photoBase64;

}
