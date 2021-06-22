package co.com.pragma.customer.servicecustomer.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.com.pragma.customer.servicecustomer.model.PhotoDTO;

@Component
public class PhotoHystrixFallBackFactory implements PhotoClientInterface {

	@Override
	public ResponseEntity<PhotoDTO> getPhoto(String id) {
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<PhotoDTO> createPhoto(PhotoDTO photo) {
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<PhotoDTO> updatePhoto(String id, PhotoDTO photo) {
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<PhotoDTO> deletePhoto(String id) {
		return ResponseEntity.notFound().build();
	}

}
