package co.com.pragma.customer.servicecustomer.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {

	private String id;

	private String name;

	private byte[] content;

	private Date createAt;

	private Date updateAt;

	private long size;

	private String contentType;
}
