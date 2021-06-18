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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.customer.servicecustomer.model.Photo;

@FeignClient(name = "service-photo", url = "http://localhost:8089")
@RequestMapping("/photos")
public interface PhotoClientInterface {

	@GetMapping
	public ResponseEntity<List<Photo>> listAllPhotos();

	@GetMapping(value = "/{id}")
	public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id);

	@PostMapping("/upload")
	public ResponseEntity<Photo> createPhoto(@RequestParam("photo") MultipartFile photo);

	@PostMapping
	public ResponseEntity<Photo> createPhoto(@RequestBody(required = true) Photo photo);

	@PutMapping(value = "/{id}")
	public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestBody(required = true) Photo photo);

	@PutMapping(value = "/upload/{id}")
	public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestParam("photo") MultipartFile photo);

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Photo> deletePhoto(@PathVariable("id") String id);
}
