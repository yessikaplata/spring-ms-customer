package co.com.pragma.customer.servicecustomer.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.com.pragma.dto.PhotoDTO;

@Component
public class PhotoHystrixFallBackFactory implements PhotoClientInterface {

	@Override
	public ResponseEntity<PhotoDTO> getPhoto(String id) {
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<PhotoDTO> createPhoto(PhotoDTO photo) {
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<PhotoDTO> updatePhoto(String id, PhotoDTO photo) {
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<PhotoDTO> deletePhoto(String id) {
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<List<PhotoDTO>> listPhotosByIds(List<String> ids) {
		return ResponseEntity.ok(new ArrayList<PhotoDTO>());
	}

}
