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
import org.springframework.boot.test.context.SpringBootTest;

import co.com.pragma.customer.servicecustomer.entity.City;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;

@SpringBootTest
public class CustomerRepositoryTest {

	@Mock
	CustomerRepositoryInterface repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		Customer customer1 = Customer.builder().identification("1102357979").name("Yessika Liliana")
				.lastName("Plata Jaimes").age(33).cityOfBirth(City.builder().id(8).build())
				.identificationType(IdentificationType.builder().id(1).build()).createAt(new Date()).build();
		Customer customer2 = Customer.builder().identification("1102357971").name("Luisa Fernanda")
				.lastName("Lopez Perez").age(18).cityOfBirth(City.builder().id(8).build())
				.identificationType(IdentificationType.builder().id(1).build()).createAt(new Date()).build();
		Customer customer3 = Customer.builder().identification("1102357972").name("Luis Francisco")
				.lastName("Duarte Castro").age(13).cityOfBirth(City.builder().id(8).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();

		List<Customer> customersYoungers = new ArrayList<Customer>();
		customersYoungers.add(customer3);
		
		List<Customer> customersAdults = new ArrayList<Customer>();
		customersAdults.add(customer1);
		customersAdults.add(customer2);
		
		Mockito.when(repository.findByIdentificationAndIdentificationType("1102357979",
				IdentificationType.builder().id(1).build())).thenReturn(customer1);
		Mockito.when(repository.findByAgeGreaterThanEqual(18)).thenReturn(customersAdults);
		Mockito.when(repository.findByAgeLessThan(18)).thenReturn(customersYoungers);
	}

	@Test
	public void whenFindByAgeGreaterThanEqual_thenReturnListCustomer() {
		List<Customer> customersFound = repository.findByAgeGreaterThanEqual(18);
		Assertions.assertThat(customersFound.size()).isEqualTo(2);
	}

	@Test
	public void whenFindByAgeLessThan_thenReturnListCustomer() {
		List<Customer> customersFound = repository.findByAgeLessThan(18);
		Assertions.assertThat(customersFound.size()).isEqualTo(1);
	}

	@Test
	public void whenFindByIdentificationAndIdentificationType_thenReturnCustomer() {
		Customer customerFound = repository.findByIdentificationAndIdentificationType("1102357979",
				IdentificationType.builder().id(1).build());
		Assertions.assertThat("1102357979").isEqualTo(customerFound.getIdentification());
	}
}
