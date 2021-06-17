package co.com.pragma.servicephoto.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.servicephoto.entity.Photo;
import co.com.pragma.servicephoto.service.PhotoServiceInterface;

@RestController
@RequestMapping("/photos")
public class PhotoController {

	@Autowired
	private PhotoServiceInterface photoService;

	@GetMapping
	public ResponseEntity<List<Photo>> listAllPhotos() {
		List<Photo> photos = photoService.listAllPhotos();
		if (photos == null || photos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(photos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id) {
		Photo photo = photoService.getPhoto(id);
		if (photo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(photo);
	}

	@PostMapping
	public ResponseEntity<Photo> createPhoto(@RequestParam("photo") MultipartFile photo) throws IOException {
		Photo photoDB = photoService.createPhoto(photo);
		return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestParam("photo") MultipartFile photo)
			throws IOException {
		Photo photoDB = photoService.updatePhoto(id, photo);
		return ResponseEntity.ok(photoDB);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Photo> deletePhoto(@PathVariable("id") String id) {
		Photo photo = photoService.deletePhoto(id);
		if (photo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(photo);
	}
}
