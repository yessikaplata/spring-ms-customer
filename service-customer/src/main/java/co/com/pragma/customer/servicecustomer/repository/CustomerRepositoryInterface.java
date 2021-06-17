package co.com.pragma.customer.servicecustomer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;

public interface CustomerRepositoryInterface extends JpaRepository<Customer, Long> {

	public List<Customer> findByAgeLessThan(Integer age);

	public List<Customer> findByAgeLessThanEqual(Integer age);

	public List<Customer> findByAgeGreaterThan(Integer age);

	public List<Customer> findByAgeGreaterThanEqual(Integer age);

	public List<Customer> findByAge(Integer age);

	public List<Customer> findByIdentificationType(IdentificationType identificationType);

	public Customer findByIdentificationAndIdentificationType(String identification,
			IdentificationType identificationType);
}
