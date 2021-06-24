package co.com.pragma.servicephoto.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.servicephoto.entity.Photo;
import co.com.pragma.servicephoto.exception.ServicePhotoException;
import co.com.pragma.servicephoto.model.PhotoDTO;
import co.com.pragma.servicephoto.repository.PhotoRepositoryInterface;

@Service
public class PhotoServiceImpl implements PhotoServiceInterface {

	@Autowired
	PhotoRepositoryInterface photoRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<PhotoDTO> listAllPhotos() {
		List<PhotoDTO> photosDTO = null;
		List<Photo> photos = photoRepository.findAll();
		if (photos != null) {
			photosDTO = photos.stream().map(photo -> {
				return modelMapper.map(photo, PhotoDTO.class);
			}).collect(Collectors.toList());
		}
		return photosDTO;
	}

	@Override
	public PhotoDTO getPhoto(String id) {
		PhotoDTO photoDTO = null;
		Photo photoDB = photoRepository.findById(id).orElse(null);
		if (photoDB != null) {
			photoDTO = modelMapper.map(photoDB, PhotoDTO.class);
		}
		return photoDTO;
	}

	@Override
	@Transactional
	public PhotoDTO createPhoto(MultipartFile file) throws IOException {
		Photo photo = new Photo();
		photo.setContent(file.getBytes());
		photo.setContentType(file.getContentType());
		photo.setCreateAt(new Date());
		photo.setUpdateAt(photo.getCreateAt());
		photo.setName(file.getOriginalFilename());
		photo.setSize(file.getSize());
		photo = photoRepository.save(photo);
		PhotoDTO photoDTO = modelMapper.map(photo, PhotoDTO.class);
		return photoDTO;
	}

	@Override
	@Transactional
	public PhotoDTO createPhoto(PhotoDTO photo) {
		Photo photoDB = modelMapper.map(photo, Photo.class);
		photoDB.setCreateAt(new Date());
		photoDB.setUpdateAt(photoDB.getCreateAt());
		photoDB = photoRepository.save(photoDB);
		PhotoDTO photoDTO = modelMapper.map(photoDB, PhotoDTO.class);
		return photoDTO;
	}

	@Override
	@Transactional
	public PhotoDTO updatePhoto(String id, MultipartFile file) throws IOException {
		Photo photo = photoRepository.findById(id).orElse(null);
		if (photo == null) {
			return null;
		}
		photo.setContent(file.getBytes());
		photo.setContentType(file.getContentType());
		photo.setUpdateAt(new Date());
		photo.setName(file.getOriginalFilename());
		photo.setSize(file.getSize());
		photo = photoRepository.save(photo);
		PhotoDTO photoDTO = modelMapper.map(photo, PhotoDTO.class);
		return photoDTO;
	}

	@Override
	@Transactional
	public PhotoDTO updatePhoto(String id, PhotoDTO photo) {
		Photo photoDB = photoRepository.findById(id).orElse(null);
		if (photoDB == null) {
			return null;
		}
		photoDB.setContent(photo.getContent());
		photoDB.setContentType(photo.getContentType());
		photoDB.setName(photo.getName());
		photoDB.setSize(photo.getSize());
		photoDB.setUpdateAt(new Date());
		photoDB = photoRepository.save(photoDB);
		PhotoDTO photoDTO = modelMapper.map(photoDB, PhotoDTO.class);
		return photoDTO;
	}

	@Override
	@Transactional
	public void deletePhoto(String id) {
		Photo photo = photoRepository.findById(id).orElse(null);
		if (photo == null) {
			throw new ServicePhotoException(HttpStatus.BAD_REQUEST, "Photo not exists in database");
		}
		photoRepository.delete(photo);
	}

	@Override
	public List<PhotoDTO> listPhotosByIds(List<String> ids) {
		List<PhotoDTO> photosDTO = null;
		List<Photo> photos = photoRepository.findByIdIn(ids);
		if (photos != null) {
			photosDTO = photos.stream().map(photo -> {
				return modelMapper.map(photo, PhotoDTO.class);
			}).collect(Collectors.toList());
		}
		return photosDTO;
	}

}
