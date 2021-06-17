package co.com.pragma.servicephoto.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id) {
		Photo photo = photoService.getPhoto(id);
		if (photo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(photo);
	}

	@PostMapping
	public ResponseEntity<Photo> createPhoto(@RequestParam("photo") MultipartFile photo, Model model)
			throws IOException {
		Photo photoDB = photoService.createPhoto(photo);
		return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
	}
}
