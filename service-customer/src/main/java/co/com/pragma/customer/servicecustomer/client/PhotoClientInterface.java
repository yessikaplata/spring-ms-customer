package co.com.pragma.customer.servicecustomer.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.pragma.dto.PhotoDTO;

@FeignClient(name = "service-photo", path = "/photos", fallback = PhotoHystrixFallBackFactory.class)
public interface PhotoClientInterface {

	@GetMapping(value = "/{id}")
	public ResponseEntity<PhotoDTO> getPhoto(@PathVariable("id") String id);

	@PostMapping
	public ResponseEntity<PhotoDTO> createPhoto(@RequestBody(required = true) PhotoDTO photo);

	@PutMapping(value = "/{id}")
	public ResponseEntity<PhotoDTO> updatePhoto(@PathVariable("id") String id, @RequestBody(required = true) PhotoDTO photo);


	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePhoto(@PathVariable("id") String id);
	
	@PostMapping("/ids")
	public ResponseEntity<List<PhotoDTO>> listPhotosByIds(@RequestBody(required = true) List<String> ids);
}
