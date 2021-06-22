package co.com.pragma.customer.servicecustomer.controller;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket customerApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("co.com.pragma.customer.servicecustomer.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

		
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Customers API", 
	      "API for customer management.", 
	      "1.0", 
	      "Terms of service", 
	      new Contact("Yessika Plata", "www.linkedin.com/in/yessika-liliana-plata-jaimes", "yessikaliliana@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}

}
