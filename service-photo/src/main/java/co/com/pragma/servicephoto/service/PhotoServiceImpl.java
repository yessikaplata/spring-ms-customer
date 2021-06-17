package co.com.pragma.servicephoto.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.servicephoto.entity.Photo;
import co.com.pragma.servicephoto.exception.ServicePhotoException;
import co.com.pragma.servicephoto.repository.PhotoRepositoryInterface;

@Service
public class PhotoServiceImpl implements PhotoServiceInterface {

	@Autowired
	PhotoRepositoryInterface photoRepository;

	@Override
	public List<Photo> listAllPhotos() {
		return photoRepository.findAll();
	}

	@Override
	public Photo createPhoto(MultipartFile file) throws IOException {
		Photo photo = new Photo();
		photo.setContent(new Binary(file.getBytes()));
		photo.setContenttype(file.getContentType());
		photo.setCreateAt(new Date());
		photo.setUpdateAt(photo.getCreateAt());
		photo.setName(file.getOriginalFilename());
		photo.setSize(file.getSize());
		return photoRepository.save(photo);
	}

	@Override
	public Photo getPhoto(String id) {
		return photoRepository.findById(id).orElse(null);
	}

	@Override
	public Photo updatePhoto(String id, MultipartFile file) throws IOException {
		Photo photo = photoRepository.findById(id).orElse(null);
		if(photo==null) {
			return null;
		}
		photo.setContent(new Binary(file.getBytes()));
		photo.setContenttype(file.getContentType());
		photo.setUpdateAt(new Date());
		photo.setName(file.getOriginalFilename());
		photo.setSize(file.getSize());
		return photoRepository.save(photo);
	}

	@Override
	public Photo deletePhoto(String id) {
		Photo photo = photoRepository.findById(id).orElse(null);
		if(photo==null) {
			throw new ServicePhotoException(HttpStatus.BAD_REQUEST, "Photo not exists in database");
		}
		photoRepository.delete(photo);
		return photo;
	}

}
