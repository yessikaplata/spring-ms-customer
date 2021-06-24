package co.com.pragma.servicephoto.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.servicephoto.model.PhotoDTO;
import co.com.pragma.servicephoto.service.PhotoServiceInterface;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/photos")
public class PhotoController {

	@Autowired
	private PhotoServiceInterface photoService;

	@GetMapping
	@ApiOperation(value = "Find all photos.", notes = "Find all photos in databases.")
	public ResponseEntity<List<PhotoDTO>> listAllPhotos() {
		List<PhotoDTO> photos = photoService.listAllPhotos();
		return ResponseEntity.ok(photos);
	}
	
	@GetMapping("/ids")
	@ApiOperation(value = "Find all photos.", notes = "Find all photos in databases.")
	public ResponseEntity<List<PhotoDTO>> listPhotosByIds(@RequestParam("ids") List<String> ids) {
		List<PhotoDTO> photos = photoService.listPhotosByIds(ids);
		return ResponseEntity.ok(photos);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Find photo by id.", notes = "Provide an ID to look up specific photo.")
	public ResponseEntity<PhotoDTO> getPhoto(@PathVariable("id") String id) {
		PhotoDTO photo = photoService.getPhoto(id);
		if (photo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(photo);
	}

	@PostMapping
	@ApiOperation(value = "Create photo.", notes = "Provide photo data to create it.")
	public ResponseEntity<PhotoDTO> createPhoto(@RequestBody(required = true) PhotoDTO photo) {
		PhotoDTO photoDB = photoService.createPhoto(photo);
		return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
	}

	@PostMapping("/upload")
	@ApiOperation(value = "Create photo.", notes = "Provide file to create it.")
	public ResponseEntity<PhotoDTO> createPhoto(@RequestParam("photo") MultipartFile photo) throws IOException {
		PhotoDTO photoDB = photoService.createPhoto(photo);
		return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Update photo.", notes = "Provide an ID and photo data to update it.")
	public ResponseEntity<PhotoDTO> updatePhoto(@PathVariable("id") String id,
			@RequestBody(required = true) PhotoDTO photo) {
		PhotoDTO photoDB = photoService.updatePhoto(id, photo);
		return ResponseEntity.ok(photoDB);
	}

	@PutMapping(value = "/upload/{id}")
	@ApiOperation(value = "Update photo.", notes = "Provide an ID and file to update it.")
	public ResponseEntity<PhotoDTO> updatePhoto(@PathVariable("id") String id,
			@RequestParam("photo") MultipartFile photo) throws IOException {
		PhotoDTO photoDB = photoService.updatePhoto(id, photo);
		return ResponseEntity.ok(photoDB);
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete photo.", notes = "Provide an ID to delete specific photo.")
	public ResponseEntity<Void> deletePhoto(@PathVariable("id") String id) {
		photoService.deletePhoto(id);
		return ResponseEntity.ok().build();
	}
}
