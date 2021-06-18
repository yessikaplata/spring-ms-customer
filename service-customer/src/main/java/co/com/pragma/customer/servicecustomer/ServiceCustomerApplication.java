package co.com.pragma.customer.servicecustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCustomerApplication.class, args);
	}

}
