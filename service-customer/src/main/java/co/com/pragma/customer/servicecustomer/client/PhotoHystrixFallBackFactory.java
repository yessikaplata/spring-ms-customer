package co.com.pragma.customer.servicecustomer.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.com.pragma.customer.servicecustomer.model.Photo;

@Component
public class PhotoHystrixFallBackFactory implements PhotoClientInterface {

	@Override
	public ResponseEntity<Photo> getPhoto(String id) {
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Photo> createPhoto(Photo photo) {
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Photo> updatePhoto(String id, Photo photo) {
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Photo> deletePhoto(String id) {
		return ResponseEntity.notFound().build();
	}

}
