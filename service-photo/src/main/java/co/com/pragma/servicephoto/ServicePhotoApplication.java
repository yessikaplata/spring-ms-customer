package co.com.pragma.servicephoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServicePhotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePhotoApplication.class, args);
	}

}
