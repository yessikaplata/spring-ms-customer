package co.com.pragma.customer.servicecustomer.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.pragma.customer.servicecustomer.entity.Photo;

public interface PhotoCustomerRepositoryInterface extends MongoRepository<Photo, BigInteger> {

	public Photo findByCustomerId(Long customerId);
}
