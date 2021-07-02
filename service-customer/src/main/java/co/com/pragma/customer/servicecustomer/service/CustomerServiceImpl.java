package co.com.pragma.customer.servicecustomer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.pragma.customer.servicecustomer.client.PhotoClientInterface;
import co.com.pragma.customer.servicecustomer.entity.City;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.model.CustomerDTO;
import co.com.pragma.dto.PhotoDTO;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServiceInterface {

	private final CustomerRepositoryInterface customerRepository;

	private final PhotoClientInterface photoClient;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<CustomerDTO> listAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return buildListCustomersDTOWithPhoto(customers);
	}

	@Override
	public CustomerDTO getCustomer(int identificationType, String identification) {

		Customer customer = customerRepository.findByIdentificationAndIdentificationType(identification,
				IdentificationType.builder().id(identificationType).build());

		if (customer == null) {
			throw new ServiceCustomerException(HttpStatus.NOT_FOUND, "Customer not exists in database");
		}
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		if (customer.getPhotoId() != null) {
			PhotoDTO photo = photoClient.getPhoto(customer.getPhotoId()).getBody();
			customerDTO.setPhoto(photo);
		}
		return customerDTO;
	}

	@Transactional
	@Override
	public CustomerDTO createCustomer(CustomerDTO customer) throws ServiceCustomerException {

		// validate customer not exist in database
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				IdentificationType.builder().id(customer.getIdentificationType().getId()).build());

		if (customerDB != null) {
			throw new ServiceCustomerException(HttpStatus.BAD_REQUEST, "Customer exists in database");
		}

		// save customer
		customerDB = modelMapper.map(customer, Customer.class);
		customerDB.setCreateAt(new Date());
		customerDB.setUpdateAt(customerDB.getCreateAt());
		customerDB = customerRepository.save(customerDB);

		// save photo
		PhotoDTO photo = null;
		if (customer.getPhoto() != null) {
			photo = photoClient.createPhoto(customer.getPhoto()).getBody();
			if (photo != null) {
				customerDB.setPhotoId(photo.getId());
			}
		}

		// convert entity to dto
		customer = modelMapper.map(customerDB, CustomerDTO.class);
		customer.setPhoto(photo);
		return customer;
	}

	@Transactional
	@Override
	public CustomerDTO updateCustomer(CustomerDTO customer) {

		// validate customer exist in database
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				IdentificationType.builder().id(customer.getIdentificationType().getId()).build());
		if (customerDB == null) {
			throw new ServiceCustomerException(HttpStatus.BAD_REQUEST, "Customer not exists in database");
		}

		// update data for customer entity
		customerDB.setAge(customer.getAge());
		customerDB.setCityOfBirth(modelMapper.map(customer.getCityOfBirth(), City.class));
		customerDB.setLastName(customer.getLastName());
		customerDB.setName(customer.getName());
		customerDB.setUpdateAt(new Date());
		customerRepository.save(customerDB);

		// create or update photo
		PhotoDTO photo = null;
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
		}

		// convert entity to dto
		customer = modelMapper.map(customerDB, CustomerDTO.class);
		customer.setPhoto(photo);
		return customer;
	}

	@Transactional
	@Override
	public void deleteCustomer(int identificationType, String identification) {

		// validate customer exist in database
		Customer customerDB = customerRepository.findByIdentificationAndIdentificationType(identification,
				IdentificationType.builder().id(identificationType).build());
		if (customerDB == null) {
			throw new ServiceCustomerException(HttpStatus.BAD_REQUEST, "Customer not exists in database");
		}

		// delete customer
		customerRepository.delete(customerDB);

		// delte photo
		if (customerDB.getPhotoId() != null) {
			photoClient.deletePhoto(customerDB.getPhotoId());
		}
	}

	@Override
	public List<CustomerDTO> listCustomersByAge(ComparatorEnum comparatorEnum, int age) {

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
		return buildListCustomersDTOWithPhoto(customers);
	}

	private List<CustomerDTO> buildListCustomersDTOWithPhoto(List<Customer> customers) {

		List<CustomerDTO> customersDTO = null;

		if (customers != null && !customers.isEmpty()) {
			CustomerDTO customerDTO = null;
			Map<String, CustomerDTO> mapa = new HashMap<String, CustomerDTO>();
			customersDTO = new ArrayList<CustomerDTO>();
			
			//add customers without photo and build map customers with photos
			for (Customer c : customers) {
				customerDTO = modelMapper.map(c, CustomerDTO.class);
				if (c.getPhotoId() != null && !c.getPhotoId().isBlank()) {
					mapa.put(c.getPhotoId(), customerDTO);
				} else {
					customersDTO.add(customerDTO);
				}
			}
			
			//add customers with photo
			if (!mapa.isEmpty()) {
				List<PhotoDTO> photos = photoClient.listPhotosByIds(new ArrayList<String>(mapa.keySet())).getBody();
				Map<String, PhotoDTO> mapPhotos = photos.stream()
						.collect(Collectors.toMap(PhotoDTO::getId, Function.identity()));
				PhotoDTO photoDTO = null;
				for (Map.Entry<String, CustomerDTO> c : mapa.entrySet()) {
					customerDTO = c.getValue();
					photoDTO = mapPhotos.get(customerDTO.getPhoto().getId());
					customerDTO.setPhoto(photoDTO);
					customersDTO.add(customerDTO);
				}
			}
		}
		return customersDTO;
	}

}
