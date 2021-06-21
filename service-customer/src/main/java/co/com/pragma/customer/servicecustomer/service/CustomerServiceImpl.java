package co.com.pragma.customer.servicecustomer.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.pragma.customer.servicecustomer.client.PhotoClientInterface;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.model.Photo;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServiceInterface {

	private final CustomerRepositoryInterface customerRepository;

	@Autowired
	private PhotoClientInterface photoClient;

	@Override
	public List<Customer> listAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return findPhotoCustomers(customers);
	}

	@Override
	public Customer getCustomer(int identificationType, String identification) {
		Customer customer = customerRepository.findByIdentificationAndIdentificationType(identification,
				IdentificationType.builder().id(identificationType).build());
		if (customer != null && customer.getPhotoId() != null) {
			Photo photo = photoClient.getPhoto(customer.getPhotoId()).getBody();
			customer.setPhoto(photo);
		}
		return customer;
	}

	@Transactional
	@Override
	public Customer createCustomer(Customer customer) throws ServiceCustomerException {
		Photo photo = null;
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				IdentificationType.builder().id(customer.getIdentificationType().getId()).build());
		
		if (customerDB != null) {
			throw new ServiceCustomerException(HttpStatus.BAD_REQUEST, "Customer exists in database");
		}

		// save customer
		customer.setCreateAt(new Date());
		customer.setUpdateAt(customer.getCreateAt());

		// save photo and update customer
		if (customer.getPhoto() != null) {
			photo = photoClient.createPhoto(customer.getPhoto()).getBody();
			if (photo != null) {
				customer.setPhotoId(photo.getId());
				customer.setPhoto(photo);
			}
		}
		customerDB = customerRepository.save(customer);
		customerDB.setPhoto(photo);
		return customerDB;
	}

	@Transactional
	@Override
	public Customer updateCustomer(Customer customer) {
		Photo photo = null;
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				IdentificationType.builder().id(customer.getIdentificationType().getId()).build());
		if (customerDB == null) {
			return null;
		}

		// update customer
		customerDB.setAge(customer.getAge());
		customerDB.setCityOfBirth(customer.getCityOfBirth());
		customerDB.setLastName(customer.getLastName());
		customerDB.setName(customer.getName());
		customerDB.setUpdateAt(new Date());

		// update or save photo
		if (customer.getPhoto() != null) {
			photo = photoClient.getPhoto(customerDB.getPhotoId()).getBody();
			if (photo == null) {
				photo = photoClient.createPhoto(customer.getPhoto()).getBody();
			} else {
				photo.setContent(customer.getPhoto().getContent());
				photo.setContentType(customer.getPhoto().getContentType());
				photo.setUpdateAt(new Date());
				photo.setName(customer.getPhoto().getName());
				photo.setSize(customer.getPhoto().getSize());
				photoClient.updatePhoto(photo.getId(), photo);
			}
			customerDB.setPhotoId(photo.getId());
			customerDB.setPhoto(photo);
		}
		customerRepository.save(customerDB);
		customerDB.setPhoto(photo);
		return customerDB;
	}

	@Transactional
	@Override
	public Customer deleteCustomer(int identificationType, String identification) {
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(identification,
				IdentificationType.builder().id(identificationType).build());
		if (customerDB == null) {
			return null;
		}
		customerRepository.delete(customerDB);

		if (customerDB.getPhotoId() != null) {
			photoClient.deletePhoto(customerDB.getPhotoId());
		}
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
		return findPhotoCustomers(customers);
	}

	private List<Customer> findPhotoCustomers(List<Customer> customers) {
		if (customers != null) {
			Photo photo = null;
			for (Customer customer : customers) {
				photo = photoClient.getPhoto(customer.getPhotoId()).getBody();
				customer.setPhoto(photo);
			}
		}
		return customers;
	}

}
