package co.com.pragma.servicephoto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.pragma.servicephoto.entity.Photo;

public interface PhotoRepositoryInterface extends MongoRepository<Photo, String>{

}
