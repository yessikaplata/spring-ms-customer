package co.com.pragma.customer.servicecustomer.service;

import java.util.List;

import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.enums.ComparatorEnum;
import co.com.pragma.customer.servicecustomer.error.ServiceCustomerException;

public interface CustomerServiceInterface {

	public List<Customer> listAllCustomers();

	public Customer getCustomer(int identificationType, String identification);

	public Customer createCustomer(Customer customer) throws ServiceCustomerException;

	public Customer updateCustomer(Customer customer);

	public Customer deleteCustomer(int identificationType, String identification);

	public List<Customer> findByAge(ComparatorEnum comparatorEnum, int age);
}
