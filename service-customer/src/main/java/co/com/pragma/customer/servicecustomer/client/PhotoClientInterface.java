package co.com.pragma.customer.servicecustomer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.pragma.customer.servicecustomer.model.Photo;

@FeignClient(name = "service-photo", path = "/photos", fallback = PhotoHystrixFallBackFactory.class)
public interface PhotoClientInterface {

	@GetMapping(value = "/{id}")
	public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id);

	@PostMapping
	public ResponseEntity<Photo> createPhoto(@RequestBody(required = true) Photo photo);

	@PutMapping(value = "/{id}")
	public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestBody(required = true) Photo photo);


	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Photo> deletePhoto(@PathVariable("id") String id);
}
