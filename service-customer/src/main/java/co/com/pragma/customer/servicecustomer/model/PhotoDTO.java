package co.com.pragma.customer.servicecustomer.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {

	private String id;

	private String name;

	private byte[] content;

	@JsonIgnore
	private Date createAt;
	
	@JsonIgnore
	private Date updateAt;

	private long size;

	private String contentType;
}
