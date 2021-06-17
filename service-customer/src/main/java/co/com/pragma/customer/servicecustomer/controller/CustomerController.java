package co.com.pragma.customer.servicecustomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.validation.Valid;

import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.service.CustomerServiceInterface;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

	@Autowired
	private CustomerServiceInterface service;

	@GetMapping
	public ResponseEntity<List<Customer>> listCustomers(
			@RequestParam(name = "comparator", required = false) String comparator,
			@RequestParam(name = "edad", required = false) Integer edad) {
		List<Customer> customers = null;
		if (comparator != null && edad != null) {
			ComparatorEnum comparatorEnum = ComparatorEnum.valueOf(comparator.toUpperCase());
			customers = service.findByAge(comparatorEnum, edad);
		} else {
			customers = service.listAllCustomers();
		}
		if (customers == null || customers.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(customers);
	}

	@GetMapping(value = "/customer")
	public ResponseEntity<Customer> getCustomer(@RequestParam(name = "type_id", required = true) int typeId,
			@RequestParam(name = "identification", required = true) String identification) {
		Customer customer = service.getCustomer(typeId, identification);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody(required = true) Customer customer) throws ServiceCustomerException {
		Customer customerCreated = service.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
	}

	@PutMapping
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody(required = true) Customer customer) {
		Customer customerUpdated = service.updateCustomer(customer);
		if (customerUpdated == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customerUpdated);
	}

	@DeleteMapping(value = "/customer")
	public ResponseEntity<Customer> deleteCustomer(@RequestParam(name = "type_id", required = true) int typeId,
			@RequestParam(name = "identification", required = true) String identification) {
		Customer customerDeleted = service.deleteCustomer(typeId, identification);
		if (customerDeleted == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customerDeleted);
	}

}
