package co.com.pragma.customer.servicecustomer.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.pragma.customer.servicecustomer.client.PhotoClientInterface;
import co.com.pragma.customer.servicecustomer.entity.City;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.model.CustomerDTO;
import co.com.pragma.customer.servicecustomer.model.PhotoDTO;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServiceInterface {

	private final CustomerRepositoryInterface customerRepository;

	@Autowired
	private PhotoClientInterface photoClient;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<CustomerDTO> listAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return findPhotoCustomers(customers);
	}

	@Override
	public CustomerDTO getCustomer(int identificationType, String identification) {
		CustomerDTO customerDTO = null;
		Customer customer = customerRepository.findByIdentificationAndIdentificationType(identification,
				IdentificationType.builder().id(identificationType).build());
		if (customer != null) {
			customerDTO = modelMapper.map(customer, CustomerDTO.class);
			if (customer.getPhotoId() != null) {
				PhotoDTO photo = photoClient.getPhoto(customer.getPhotoId()).getBody();
				customerDTO.setPhoto(photo);
			}
		}
		return customerDTO;
	}

	@Transactional
	@Override
	public CustomerDTO createCustomer(CustomerDTO customer) throws ServiceCustomerException {
		PhotoDTO photo = null;
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				IdentificationType.builder().id(customer.getIdentificationType().getId()).build());

		if (customerDB != null) {
			throw new ServiceCustomerException(HttpStatus.BAD_REQUEST, "Customer exists in database");
		}

		// create object customer
		customerDB = modelMapper.map(customer, Customer.class);
		customerDB.setCreateAt(new Date());
		customerDB.setUpdateAt(customerDB.getCreateAt());

		// insert photo into customer
		if (customer.getPhoto() != null) {
			photo = photoClient.createPhoto(customer.getPhoto()).getBody();
			if (photo != null) {
				customerDB.setPhotoId(photo.getId());
				customerDB.setPhoto(photo);
			}
		}
		customerRepository.save(customerDB);
		return modelMapper.map(customerDB, CustomerDTO.class);
	}

	@Transactional
	@Override
	public CustomerDTO updateCustomer(CustomerDTO customer) {
		PhotoDTO photo = null;
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				IdentificationType.builder().id(customer.getIdentificationType().getId()).build());
		if (customerDB == null) {
			return null;
		}

		// update customer
		customerDB.setAge(customer.getAge());
		customerDB.setCityOfBirth(modelMapper.map(customer.getCityOfBirth(), City.class));
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
				photo.setName(customer.getPhoto().getName());
				photo.setSize(customer.getPhoto().getSize());
				photoClient.updatePhoto(photo.getId(), photo);
			}
			customerDB.setPhotoId(photo.getId());
			customerDB.setPhoto(photo);
		}
		customerRepository.save(customerDB);
		customerDB.setPhoto(photo);
		return modelMapper.map(customerDB, CustomerDTO.class);
	}

	@Transactional
	@Override
	public void deleteCustomer(int identificationType, String identification) {
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(identification,
				IdentificationType.builder().id(identificationType).build());
		if (customerDB == null) {
			throw new ServiceCustomerException(HttpStatus.BAD_REQUEST, "Customer exists in database");
		}
		customerRepository.delete(customerDB);

		if (customerDB.getPhotoId() != null) {
			photoClient.deletePhoto(customerDB.getPhotoId());
		}
	}

	@Override
	public List<CustomerDTO> findByAge(ComparatorEnum comparatorEnum, int age) {
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

	private List<CustomerDTO> findPhotoCustomers(List<Customer> customers) {
		List<CustomerDTO> customersDTO = null;
		if (customers != null) {
			customersDTO = customers.stream().map(c -> {
				CustomerDTO customerDTO = modelMapper.map(c, CustomerDTO.class);
				PhotoDTO photo = photoClient.getPhoto(c.getPhotoId()).getBody();
				customerDTO.setPhoto(photo);
				return customerDTO;
			}).collect(Collectors.toList());
		}
		return customersDTO;
	}

}
