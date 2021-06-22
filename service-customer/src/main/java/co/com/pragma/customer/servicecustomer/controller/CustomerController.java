package co.com.pragma.customer.servicecustomer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.service.CustomerServiceInterface;
import io.swagger.annotations.ApiOperation;

/**
 * RESTApi Customer
 * 
 * @author Yessika.Plata
 *
 */
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

	@Autowired
	private CustomerServiceInterface service;

	@GetMapping
	@ApiOperation(value = "Find all customer", notes = "Find all customer. To filter by age, provide the age and the comparator.")
	public ResponseEntity<List<Customer>> listCustomers(
			@RequestParam(name = "comparator", required = false) ComparatorEnum comparator,
			@RequestParam(name = "age", required = false) Integer age) {
		List<Customer> customers = null;
		if (comparator != null && age != null) {
			customers = service.findByAge(comparator, age);
		} else {
			customers = service.listAllCustomers();
		}
		if (customers == null || customers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(customers);
	}

	@GetMapping(value = "/{type_id}/{identification}")
	@ApiOperation(value = "Find a customer by type and ID number", notes = "Provide a type and ID number to look up specific customer.")
	public ResponseEntity<Customer> getCustomer(@PathVariable("type_id") int typeId,
			@PathVariable("identification") String identification) {
		Customer customer = service.getCustomer(typeId, identification);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}

	@PostMapping
	@ApiOperation(value = "Create a customer", notes = "Provide customer data to create it.")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody(required = true) Customer customer)
			throws ServiceCustomerException {
		Customer customerCreated = service.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
	}

	@PutMapping
	@ApiOperation(value = "Update a customer", notes = "Provide customer data to update it.")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody(required = true) Customer customer) {
		Customer customerUpdated = service.updateCustomer(customer);
		if (customerUpdated == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customerUpdated);
	}

	@DeleteMapping(value = "/{type_id}/{identification}")
	@ApiOperation(value = "Delete a customer", notes = "Provide a type and ID number to delete specific customer.")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("type_id") int typeId,
			@PathVariable("identification") String identification) {
		Customer customerDeleted = service.deleteCustomer(typeId, identification);
		if (customerDeleted == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

}
