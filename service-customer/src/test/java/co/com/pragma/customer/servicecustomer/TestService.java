package co.com.pragma.customer.servicecustomer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import co.com.pragma.customer.servicecustomer.client.PhotoClientInterface;
import co.com.pragma.customer.servicecustomer.entity.City;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.model.CustomerDTO;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;
import co.com.pragma.customer.servicecustomer.service.CustomerServiceImpl;
import co.com.pragma.dto.PhotoDTO;

@ExtendWith(MockitoExtension.class)
public class TestService {

	@Mock
	CustomerRepositoryInterface repository;

	@Mock
	PhotoClientInterface photoClient;

	@InjectMocks
	CustomerServiceImpl service;




	private ModelMapper modelMapper = new ModelMapper();

	@Test
	void testListAllCustomersWithoutPhoto() {
		// given
		Customer customer1 = Customer.builder().identification("123456789").name("Luisa Fernanda").lastName("Triana")
				.age(12).cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
		Customer customer2 = Customer.builder().identification("123456780").name("Maria Alejandra").lastName("Duarte")
				.age(10).cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
		Customer customer3 = Customer.builder().identification("23434544").name("Luis Felipe").lastName("Mantilla")
				.age(35).cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();

		CustomerDTO customerDTO1 = modelMapper.map(customer1, CustomerDTO.class);
		CustomerDTO customerDTO2 = modelMapper.map(customer2, CustomerDTO.class);
		CustomerDTO customerDTO3 = modelMapper.map(customer3, CustomerDTO.class);

		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		when(repository.findAll()).thenReturn(customers);

		// when
		List<CustomerDTO> customersService = service.listAllCustomers();
		// then
		assertEquals(3, customersService.size());
		assertEquals(customerDTO1, customersService.get(0));
		assertEquals(customerDTO2, customersService.get(1));
		assertEquals(customerDTO3, customersService.get(2));
	}

	@Test
	void testListAllCustomersWithPhoto() {
		// given customers
		Customer customer1 = Customer.builder().identification("123456789").name("Luisa Fernanda").lastName("Triana")
				.photoId("photo001").age(12).cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
		Customer customer2 = Customer.builder().identification("123456780").name("Maria Alejandra").lastName("Duarte").photoId("photo002")
				.age(10).cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
		Customer customer3 = Customer.builder().identification("23434544").name("Luis Felipe").lastName("Mantilla").photoId("photo003")
				.age(35).cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();

		CustomerDTO customerDTO1 = modelMapper.map(customer1, CustomerDTO.class);
		CustomerDTO customerDTO2 = modelMapper.map(customer2, CustomerDTO.class);
		CustomerDTO customerDTO3 = modelMapper.map(customer3, CustomerDTO.class);

		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		when(repository.findAll()).thenReturn(customers);

		// given photos
		PhotoDTO photoDTO1 = PhotoDTO.builder().id("photo001").content(null).contentType("img/jpg").name("photo001")
				.size(100).build();
		PhotoDTO photoDTO2 = PhotoDTO.builder().id("photo002").content(null).contentType("img/jpeg").name("photo002")
				.size(100).build();
		PhotoDTO photoDTO3 = PhotoDTO.builder().id("photo003").content(null).contentType("img/png").name("photo003")
				.size(100).build();

		List<String> idsPhotos = Arrays.asList("photo001", "photo002", "photo003");
		List<PhotoDTO> photosDto = Arrays.asList(photoDTO1, photoDTO2, photoDTO3);
		
		when(photoClient.listPhotosByIds(idsPhotos))
		.thenReturn((ResponseEntity<List<PhotoDTO>>) ResponseEntity.ok(photosDto));
			

		// when
		List<CustomerDTO> customersService = service.listAllCustomers();
		// then
		assertEquals(3, customersService.size());
		assertEquals(customerDTO1, customersService.get(0));
		assertEquals(customerDTO2, customersService.get(1));
		assertEquals(customerDTO3, customersService.get(2));
		//verify(photoClient).listPhotosByIds(idsPhotos);
	}
}
