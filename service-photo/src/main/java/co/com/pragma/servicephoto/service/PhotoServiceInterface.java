package co.com.pragma.servicephoto.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.servicephoto.model.PhotoDTO;

public interface PhotoServiceInterface {

	public List<PhotoDTO> listAllPhotos();

	public PhotoDTO createPhoto(MultipartFile file) throws IOException;

	public PhotoDTO createPhoto(PhotoDTO photo);

	public PhotoDTO getPhoto(String id);

	public PhotoDTO updatePhoto(String id, PhotoDTO photo);

	public PhotoDTO updatePhoto(String id, MultipartFile file) throws IOException;

	public void deletePhoto(String id);
}
