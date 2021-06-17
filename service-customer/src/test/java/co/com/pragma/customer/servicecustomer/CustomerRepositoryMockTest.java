package co.com.pragma.customer.servicecustomer;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.com.pragma.customer.servicecustomer.entity.City;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;

@SpringBootTest
public class CustomerRepositoryMockTest {

	@Autowired
	CustomerRepositoryInterface repository;

	@Test
	public void whenFindByAgeGreaterThan_thenReturnListCustomer() {
		Customer customer1 = Customer.builder().identification("1102357979").name("Yessika Liliana")
				.lastName("Plata Jaimes").age(33).cityOfBirth(City.builder().id(8).build())
				.identificationType(IdentificationType.builder().id(1).build()).createAt(new Date()).build();
		Customer customer2 = Customer.builder().identification("1102357971").name("Luisa Fernanda")
				.lastName("Lopez Perez").age(32).cityOfBirth(City.builder().id(8).build())
				.identificationType(IdentificationType.builder().id(1).build()).createAt(new Date()).build();
		Customer customer3 = Customer.builder().identification("1102357972").name("Luis Francisco")
				.lastName("Duarte Castro").age(13).cityOfBirth(City.builder().id(8).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
		repository.save(customer1);
		repository.save(customer2);
		repository.save(customer3);

		List<Customer> customersFound = repository.findByAgeGreaterThan(30);
		Assertions.assertThat(customersFound.size()).isEqualTo(2);
	}

	@Test
	public void whenFindByIdentificationAndIdentificationType_thenReturnCustomer() {

		Customer customer = Customer.builder().identification("919235678").name("Daniel").lastName("Martinez Duarte")
				.age(21).cityOfBirth(City.builder().id(3).build())
				.identificationType(IdentificationType.builder().id(1).build()).createAt(new Date()).build();
		Customer customerSave = repository.save(customer);
		Customer customerFound = repository.findByIdentificationAndIdentificationType(customer.getIdentification(),
				customer.getIdentificationType());
		Assertions.assertThat(customerFound.getId()).isEqualTo(customerSave.getId());
	}

}
