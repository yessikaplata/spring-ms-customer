package co.com.pragma.dto;

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

	private long size;

	private String contentType;
}
