package co.com.pragma.customer.servicecustomer;

import static co.com.pragma.customer.servicecustomer.DataTest.getCustomer1;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomer1DTO;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomer2;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomer2DTO;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomer3;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomer3DTO;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomerPhoto1;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomerPhoto1DTO;
import static co.com.pragma.customer.servicecustomer.DataTest.getCustomerSave1;
import static co.com.pragma.customer.servicecustomer.DataTest.getPhoto1DTO;
import static co.com.pragma.customer.servicecustomer.DataTest.getPhoto2DTO;
import static co.com.pragma.customer.servicecustomer.DataTest.listCustomers;
import static co.com.pragma.customer.servicecustomer.DataTest.listCustomersWithPhotos;
import static co.com.pragma.customer.servicecustomer.DataTest.listPhotos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import co.com.pragma.customer.servicecustomer.client.PhotoClientInterface;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
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

	@BeforeEach
	void setUp() {
		service = new CustomerServiceImpl(repository, photoClient);
	}

	@Test
	void testListAllCustomersWithoutPhoto() {
		// given
		when(repository.findAll()).thenReturn(listCustomers());

		// when
		List<CustomerDTO> customersService = service.listAllCustomers();
		// then
		assertEquals(3, customersService.size());
		assertEquals(getCustomer1DTO(), customersService.get(0));
		assertEquals(getCustomer2DTO(), customersService.get(1));
		assertEquals(getCustomer3DTO(), customersService.get(2));
	}

	@Test
	void testListAllCustomersWithPhoto() {
		// given
		when(photoClient.listPhotosByIds(anyList()))
				.thenReturn((ResponseEntity<List<PhotoDTO>>) ResponseEntity.ok(listPhotos()));
		when(repository.findAll()).thenReturn(listCustomersWithPhotos());

		// when
		List<CustomerDTO> customersService = service.listAllCustomers();

		// then
		assertEquals(3, customersService.size());
		assertEquals("photo001", customersService.stream().filter(x -> x.getIdentification().equals("123456789"))
				.findFirst().get().getPhoto().getId());
		assertEquals("photo002", customersService.stream().filter(x -> x.getIdentification().equals("123456780"))
				.findFirst().get().getPhoto().getId());
		assertEquals("photo003", customersService.stream().filter(x -> x.getIdentification().equals("23434544"))
				.findFirst().get().getPhoto().getId());

	}

	@Test
	void testGetCustomerWithoutPhoto() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(getCustomer1());

		// when
		CustomerDTO customerService = service.getCustomer(2, "123456789");

		// then
		assertEquals("123456789", customerService.getIdentification());
		assertEquals("Luisa Fernanda", customerService.getName());
	}

	@Test
	void testGetCustomerWithPhoto() {
		// given

		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(getCustomerPhoto1());
		when(photoClient.getPhoto("photo001")).thenReturn(ResponseEntity.ok(getPhoto1DTO()));

		// when
		CustomerDTO customerService = service.getCustomer(2, "123456789");

		// then
		assertEquals("123456789", customerService.getIdentification());
		assertEquals("Luisa Fernanda", customerService.getName());
		assertEquals("photo001", customerService.getPhoto().getId());
	}

	@Test
	void testGetCustomerWhenNotExist() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(null);
		// when
		assertThrows(ServiceCustomerException.class, () -> {
			service.getCustomer(2, "123456789");
		});
	}

	@Test
	void testCreateCustomerWhenExist() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(getCustomer1());

		// when
		assertThrows(ServiceCustomerException.class, () -> {
			service.createCustomer(getCustomer1DTO());
		});
		verify(repository).findByIdentificationAndIdentificationType(anyString(), any(IdentificationType.class));
	}

	@Test
	void testCreateCustomerWithoutPhoto() {
		// given

		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(null);
		when(repository.save(any(Customer.class))).thenReturn(getCustomer1());

		// when
		CustomerDTO customerDtoService = service.createCustomer(getCustomer1DTO());
		assertNotNull(customerDtoService);
		assertNull(customerDtoService.getPhoto());
		verify(repository).save(any(Customer.class));
	}

	@Test
	void testCreateCustomerWithPhoto() {
		// given

		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(null);
		when(repository.save(any(Customer.class))).thenReturn(getCustomerPhoto1());
		when(photoClient.createPhoto(any(PhotoDTO.class))).thenReturn(ResponseEntity.ok(getPhoto1DTO()));

		// when
		CustomerDTO customerDtoService = service.createCustomer(getCustomerPhoto1DTO());
		assertNotNull(customerDtoService);
		assertNotNull(customerDtoService.getPhoto());
		assertEquals("photo001", customerDtoService.getPhoto().getId());
		verify(repository).save(any(Customer.class));
		verify(photoClient).createPhoto(any(PhotoDTO.class));
	}

	@Test
	void testUpdateCustomerWhenNotExist() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(null);

		// when then
		assertThrows(ServiceCustomerException.class, () -> {
			service.updateCustomer(getCustomer1DTO());
		});
	}

	@Test
	void testUpdateCustomerWithoutPhoto() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(getCustomer1());
		when(repository.save(any(Customer.class))).thenReturn(getCustomerSave1());
		when(photoClient.getPhoto(anyString())).thenReturn(ResponseEntity.ok(null));
		when(photoClient.createPhoto(any(PhotoDTO.class))).thenReturn(ResponseEntity.ok(getPhoto1DTO()));
		// when
		CustomerDTO customerDtoService = service.updateCustomer(getCustomerPhoto1DTO());
		// then
		assertNotNull(customerDtoService);
		assertNotNull(customerDtoService.getPhoto());
		assertEquals("photo001", customerDtoService.getPhoto().getId());
		assertEquals("Triana Jaimes", customerDtoService.getLastName());
		verify(repository).save(any(Customer.class));
		verify(photoClient).getPhoto(anyString());
		verify(photoClient).createPhoto(any(PhotoDTO.class));
	}

	@Test
	void testUpdateCustomerWithPhoto() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(getCustomer1());
		when(repository.save(any(Customer.class))).thenReturn(getCustomerSave1());
		when(photoClient.getPhoto(anyString())).thenReturn(ResponseEntity.ok(getPhoto1DTO()));
		when(photoClient.updatePhoto(anyString(), any(PhotoDTO.class))).thenReturn(ResponseEntity.ok(getPhoto2DTO()));
		// when
		CustomerDTO customerDtoService = service.updateCustomer(getCustomerPhoto1DTO());
		// then
		assertNotNull(customerDtoService);
		assertNotNull(customerDtoService.getPhoto());
		assertEquals("photo002", customerDtoService.getPhoto().getId());
		assertEquals("Triana Jaimes", customerDtoService.getLastName());
		verify(repository).save(any(Customer.class));
		verify(photoClient).getPhoto(anyString());
		verify(photoClient).updatePhoto(anyString(), any(PhotoDTO.class));
	}

	@Test
	void testDeleteCustomerWhenNotExist() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(null);
		// when then
		assertThrows(ServiceCustomerException.class, () -> {
			service.deleteCustomer(2, "123456789");
		});

	}

	@Test
	void testDeleteCustomerWhenExistWithoutPhoto() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(getCustomer1());
		// when then
		service.deleteCustomer(2, "123456789");
		verify(repository).findByIdentificationAndIdentificationType(anyString(), any(IdentificationType.class));
		verify(repository).delete(any(Customer.class));
		verify(photoClient, times(0)).deletePhoto(anyString());
	}

	@Test
	void testDeleteCustomerWhenExistWithPhoto() {
		// given
		when(repository.findByIdentificationAndIdentificationType("123456789",
				IdentificationType.builder().id(2).build())).thenReturn(getCustomerPhoto1());
		// when then
		service.deleteCustomer(2, "123456789");
		verify(repository).findByIdentificationAndIdentificationType(anyString(), any(IdentificationType.class));
		verify(repository).delete(any(Customer.class));
		verify(photoClient).deletePhoto(anyString());
	}

	@Test
	void testOptionsByAges() {
		// given
		List<Customer> customers1 = Arrays.asList(getCustomer1());
		List<Customer> customers2 = Arrays.asList(getCustomer2(), getCustomer3());
		List<Customer> customers3 = Arrays.asList(getCustomer1(), getCustomer2(), getCustomer3());
		List<Customer> customers4 = Arrays.asList(getCustomer1(), getCustomer2());
		List<Customer> customers5 = Arrays.asList(getCustomer1(), getCustomer2(), getCustomer3());

		when(repository.findByAge(10)).thenReturn(customers1);
		when(repository.findByAgeGreaterThan(10)).thenReturn(customers2);
		when(repository.findByAgeGreaterThanEqual(10)).thenReturn(customers3);
		when(repository.findByAgeLessThan(35)).thenReturn(customers4);
		when(repository.findByAgeLessThanEqual(35)).thenReturn(customers5);

		// when
		List<CustomerDTO> customers1Dto = service.listCustomersByAge(ComparatorEnum.EQUALS, 10);// 1
		List<CustomerDTO> customers2Dto = service.listCustomersByAge(ComparatorEnum.GREATER, 10);// 2
		List<CustomerDTO> customers3Dto = service.listCustomersByAge(ComparatorEnum.GREATER_THAN_EQUALS, 10);// 3
		List<CustomerDTO> customers4Dto = service.listCustomersByAge(ComparatorEnum.LESS, 35);// 2
		List<CustomerDTO> customers5Dto = service.listCustomersByAge(ComparatorEnum.LESS_THAN_EQUALS, 35); // 3

		// then
		assertEquals(1, customers1Dto.size());
		assertEquals(2, customers2Dto.size());
		assertEquals(3, customers3Dto.size());
		assertEquals(2, customers4Dto.size());
		assertEquals(3, customers5Dto.size());

	}
}
