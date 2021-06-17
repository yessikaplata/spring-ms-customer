package co.com.pragma.customer.servicecustomer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.entity.Photo;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.error.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;
import co.com.pragma.customer.servicecustomer.repository.PhotoCustomerRepositoryInterface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServiceInterface {

	private final CustomerRepositoryInterface customerRepository;

	@Autowired
	private PhotoCustomerRepositoryInterface photoRepository;

	@Override
	public List<Customer> listAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomer(int identificationType, String identification) {
		Customer customer = customerRepository.findByIdentificationAndIdentificationType(identification,
				IdentificationType.builder().id(identificationType).build());
		if (customer != null) {
			Photo photo = photoRepository.findByCustomerId(customer.getId());
			if (photo != null) {
				customer.setPhotoBase64(photo.getPhotoBase64());
			}
		}
		return customer;
	}

	@Override
	public Customer createCustomer(Customer customer) throws ServiceCustomerException {
		Customer customerDB = getCustomer(customer.getIdentificationType().getId(), customer.getIdentification());
		if (customerDB != null) {
			throw new ServiceCustomerException(HttpStatus.BAD_REQUEST, "Customer exists in database");
		}
		customer.setCreateAt(new Date());
		customerDB = customerRepository.save(customer);
		Photo photo = Photo.builder().customerId(customerDB.getId()).photoBase64(customer.getPhotoBase64()).build();
		photo = photoRepository.save(photo);
		return customerRepository.save(customerDB);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer customerDB = getCustomer(customer.getIdentificationType().getId(), customer.getIdentification());
		if (customerDB == null) {
			return null;
		}
		customerDB.setAge(customer.getAge());
		customerDB.setCityOfBirth(customer.getCityOfBirth());
		customerDB.setLastName(customer.getLastName());
		customerDB.setName(customer.getName());
		customerDB.setUpdateAt(new Date());
		return customerRepository.save(customerDB);
	}

	@Override
	public Customer deleteCustomer(int identificationType, String identification) {
		Customer customerDB = getCustomer(identificationType, identification);
		if (customerDB == null) {
			return null;
		}
		customerRepository.delete(customerDB);
		return customerDB;
	}

	@Override
	public List<Customer> findByAge(ComparatorEnum comparatorEnum, int age) {
		List<Customer> customers = null;
		switch (comparatorEnum) {
		case GREATER:
			customers = customerRepository.findByAgeGreaterThan(age);
			break;
		case GREATER_THAN_EQUALS:
			customers = customerRepository.findByAgeGreaterThanEqual(age);
			break;
		case LESS:
			customers = customerRepository.findByAgeLessThan(age);
			break;
		case LESS_THAN_EQUALS:
			customers = customerRepository.findByAgeLessThanEqual(age);
			break;
		default:
			customers = customerRepository.findByAge(age);
			break;
		}
		return customers;
	}

}
