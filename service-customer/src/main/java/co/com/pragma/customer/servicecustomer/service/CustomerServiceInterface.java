package co.com.pragma.customer.servicecustomer.service;

import java.util.List;

import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.exception.ServiceCustomerException;
import co.com.pragma.customer.servicecustomer.model.CustomerDTO;

public interface CustomerServiceInterface {
	

	public List<CustomerDTO> listAllCustomers();

	public CustomerDTO getCustomer(int identificationType, String identification);

	public CustomerDTO createCustomer(CustomerDTO customer) throws ServiceCustomerException;

	public CustomerDTO updateCustomer(CustomerDTO customer);

	public void deleteCustomer(int identificationType, String identification);

	public List<CustomerDTO> listCustomersByAge(ComparatorEnum comparatorEnum, int age);
	
}
