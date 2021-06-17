package co.com.pragma.servicephoto.entity;

import java.util.Date;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "photos")
public class Photo {

	@Id
	private String id;

	private String name;

	private Binary content;

	private Date createAt;

	private Date updateAt;

	private long size;

	private String contenttype;
}
