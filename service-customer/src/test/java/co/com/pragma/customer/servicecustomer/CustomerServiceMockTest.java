package co.com.pragma.customer.servicecustomer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.com.pragma.customer.servicecustomer.entity.City;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;
import co.com.pragma.customer.servicecustomer.service.CustomerServiceImpl;
import co.com.pragma.customer.servicecustomer.service.CustomerServiceInterface;

@SpringBootTest
public class CustomerServiceMockTest {

	@Autowired
	private CustomerServiceInterface service;

	@Mock
	private CustomerRepositoryInterface repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CustomerServiceImpl(repository);
		Customer customer = Customer.builder().identification("23434544").name("Maria Alejandra").lastName("Triana")
				.age(12).cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		Mockito.when(repository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				customer.getIdentificationType())).thenReturn(customer);
		Mockito.when(repository.findAll()).thenReturn(customers);

	}

	@Test
	public void whenValidateGetIdentificationAndIdentificationType_ThenReturnCustomer() {
		Customer customerFound = service.getCustomer(2, "23434544");
		Assertions.assertThat(customerFound.getName()).isEqualTo("Maria Alejandra");
	}

	@Test
	public void whenValidateListAllCustomer_ThenReturnListCustomer() {
		List<Customer> customers = service.listAllCustomers();
		Assertions.assertThat(customers.size()).isEqualTo(1);
	}
}
