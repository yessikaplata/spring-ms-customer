package co.com.pragma.servicephoto.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.servicephoto.entity.Photo;

public interface PhotoServiceInterface {

	public List<Photo> listAllPhotos();
	
	public Photo createPhoto(MultipartFile file) throws IOException ;
	
	public Photo getPhoto(String id);
	
	public Photo updatePhoto(String id, MultipartFile file) throws IOException ;
	
	public Photo deletePhoto(String id);
}
