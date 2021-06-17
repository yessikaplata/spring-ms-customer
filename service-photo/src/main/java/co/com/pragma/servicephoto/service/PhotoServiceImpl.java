package co.com.pragma.servicephoto.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.websocket.server.ServerEndpoint;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.servicephoto.entity.Photo;
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
		photo.setName(file.getOriginalFilename());
		photo.setSize(file.getSize());
		return photoRepository.save(photo);
	}

	@Override
	public Photo getPhoto(String id) {
		return photoRepository.findById(id).get();
	}

}
