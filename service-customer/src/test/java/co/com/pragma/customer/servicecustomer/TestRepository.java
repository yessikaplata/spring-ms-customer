package co.com.pragma.customer.servicecustomer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.repository.CustomerRepositoryInterface;


@DataJpaTest
public class TestRepository {

	@Autowired
	CustomerRepositoryInterface repository;

	@Test
	void findByAgeAdultsCustomers() {
		// given
		int age = 18;
		// when
		List<Customer> customers = repository.findByAgeGreaterThanEqual(age);
		// then
		assertNotNull(customers);
		assertEquals(2, customers.size());
	}

	@Test
	void findByAgeYougersCustomers() {
		// given
		int age = 18;
		// when
		List<Customer> customers = repository.findByAgeLessThan(age);
		// then
		assertNotNull(customers);
		assertEquals(3, customers.size());
	}
	

	@Test
	void findByAgeGreaterThan() {
		// given
		int age = 30;
		// when
		List<Customer> customers = repository.findByAgeGreaterThan(age);
		// then
		assertNotNull(customers);
		assertEquals(2, customers.size());
	}

	@Test
	void findByAgeLessThanEqual() {
		// given
		int age = 15;
		// when
		List<Customer> customers = repository.findByAgeLessThanEqual(age);
		// then
		assertNotNull(customers);
		assertEquals(2, customers.size());
	}


	@Test
	void findByAge() {
		// given
		int age = 50;
		// when
		List<Customer> customers = repository.findByAge(age);
		// then
		assertNotNull(customers);
		assertEquals(1, customers.size());
	}

	@Test
	void findByIdentificationAndIdentificationType() {
		Customer customer = repository.findByIdentificationAndIdentificationType("123456781",
				IdentificationType.builder().id(2).build());
		assertNotNull(customer);
		assertEquals("Yessika", customer.getName());
	}

}
