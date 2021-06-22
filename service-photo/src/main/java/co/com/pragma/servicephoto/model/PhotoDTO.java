package co.com.pragma.servicephoto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoDTO {
	
	private String id;

	private String name;

	private byte[] content;

	private long size;

	private String contentType;
}
