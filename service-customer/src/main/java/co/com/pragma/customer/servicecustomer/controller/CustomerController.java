package co.com.pragma.customer.servicecustomer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.model.CustomerDTO;
import co.com.pragma.customer.servicecustomer.service.CustomerServiceInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * RESTApi Customer
 * 
 * @author Yessika.Plata
 *
 */
@RestController
@RequestMapping(value = "/customers")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class CustomerController {

	@Autowired
	private CustomerServiceInterface service;

	@GetMapping
	@ApiOperation(value = "Find all customer", notes = "Find all customer.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Customers found."),
			@ApiResponse(code = 204, message = "Customers not found.") })
	public ResponseEntity<List<CustomerDTO>> listCustomers() {
		List<CustomerDTO> customers = service.listAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/age/{comparator}/{age}/")
	@ApiOperation(value = "Find customer by age", notes = "Find all customer by age. To filter by age, provide the age and the comparator.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Customers found."),
			@ApiResponse(code = 204, message = "Customers not found.") })
	public ResponseEntity<List<CustomerDTO>> listCustomers(
			@PathVariable("comparator") ComparatorEnum comparator,
			@PathVariable("age") int age) {
		List<CustomerDTO> customers = service.listCustomersByAge(comparator, age);
		return ResponseEntity.ok(customers);
	}

	@GetMapping(value = "/{type_id}/{identification}")
	@ApiOperation(value = "Find a customer by type and ID number", notes = "Provide a type and ID number to look up specific customer.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Customer found."),
			@ApiResponse(code = 404, message = "Customer not found.") })
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("type_id") int typeId,
			@PathVariable("identification") String identification) {
		CustomerDTO customer = service.getCustomer(typeId, identification);
		return ResponseEntity.ok(customer);
	}

	@PostMapping
	@ApiOperation(value = "Create a customer", notes = "Provide customer data to create it.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Customer created."),
			@ApiResponse(code = 400, message = "Customer already exists.") })
	public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody(required = true) CustomerDTO customer)
			throws ServiceCustomerException {
		CustomerDTO customerCreated = service.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
	}

	@PutMapping
	@ApiOperation(value = "Update a customer", notes = "Provide customer data to update it.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Customer updated."),
			@ApiResponse(code = 404, message = "Customer not found.") })
	public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody(required = true) CustomerDTO customer) {
		CustomerDTO customerUpdated = service.updateCustomer(customer);
		return ResponseEntity.ok(customerUpdated);
	}

	@DeleteMapping(value = "/{type_id}/{identification}")
	@ApiOperation(value = "Delete a customer", notes = "Provide a type and ID number to delete specific customer.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Customer deleted."),
			@ApiResponse(code = 404, message = "Customer not found.") })
	public ResponseEntity<Void> deleteCustomer(@PathVariable("type_id") int typeId,
			@PathVariable("identification") String identification) {
		service.deleteCustomer(typeId, identification);
		return ResponseEntity.ok().build();
	}

}
