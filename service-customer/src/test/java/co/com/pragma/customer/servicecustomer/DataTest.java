package co.com.pragma.customer.servicecustomer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import co.com.pragma.customer.servicecustomer.entity.City;
import co.com.pragma.customer.servicecustomer.entity.Customer;
import co.com.pragma.customer.servicecustomer.entity.IdentificationType;
import co.com.pragma.customer.servicecustomer.model.CityDTO;
import co.com.pragma.customer.servicecustomer.model.CustomerDTO;
import co.com.pragma.customer.servicecustomer.model.IdentificationTypeDTO;
import co.com.pragma.dto.PhotoDTO;

public class DataTest {

	public static List<Customer> listCustomers() {

		return Arrays.asList(getCustomer1(), getCustomer2(), getCustomer3());
	}

	public static List<Customer> listCustomersWithPhotos() {

		return Arrays.asList(getCustomerPhoto1(), getCustomerPhoto2(), getCustomerPhoto3());
	}

	public static Customer getCustomer1() {
		return Customer.builder().identification("123456789").name("Luisa Fernanda").lastName("Triana").age(12)
				.cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
	}
	
	public static Customer getCustomerSave1() {
		return Customer.builder().identification("123456789").name("Luisa Fernanda").lastName("Triana Jaimes").age(16)
				.cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
	}

	public static Customer getCustomer2() {
		return Customer.builder().identification("123456780").name("Maria Alejandra").lastName("Duarte").age(10)
				.cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
	}

	public static Customer getCustomer3() {
		return Customer.builder().identification("23434544").name("Luis Felipe").lastName("Mantilla").age(35)
				.cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
	}

	public static Customer getCustomerPhoto1() {
		return Customer.builder().identification("123456789").name("Luisa Fernanda").lastName("Triana").age(12)
				.photoId("photo001").cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
	}

	public static Customer getCustomerPhoto2() {
		return Customer.builder().identification("123456780").name("Maria Alejandra").lastName("Duarte").age(10)
				.photoId("photo002").cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
	}

	public static Customer getCustomerPhoto3() {
		return Customer.builder().identification("23434544").name("Luis Felipe").lastName("Mantilla").age(35)
				.photoId("photo003").cityOfBirth(City.builder().id(4).build())
				.identificationType(IdentificationType.builder().id(2).build()).createAt(new Date()).build();
	}

	public static CustomerDTO getCustomer1DTO() {
		return CustomerDTO.builder().identification("123456789").name("Luisa Fernanda").lastName("Triana").age(12)
				.cityOfBirth(CityDTO.builder().id(4).build())
				.identificationType(IdentificationTypeDTO.builder().id(2).build()).build();
	}

	public static CustomerDTO getCustomer2DTO() {
		return CustomerDTO.builder().identification("123456780").name("Maria Alejandra").lastName("Duarte").age(10)
				.cityOfBirth(CityDTO.builder().id(4).build())
				.identificationType(IdentificationTypeDTO.builder().id(2).build()).build();
	}

	public static CustomerDTO getCustomer3DTO() {
		return CustomerDTO.builder().identification("23434544").name("Luis Felipe").lastName("Mantilla").age(35)
				.cityOfBirth(CityDTO.builder().id(4).build())
				.identificationType(IdentificationTypeDTO.builder().id(2).build()).build();
	}

	public static CustomerDTO getCustomerPhoto1DTO() {
		CustomerDTO cDto = getCustomer1DTO();
		cDto.setPhoto(getPhoto1DTO());
		return cDto;
	}


	public static List<PhotoDTO> listPhotos() {
		return Arrays.asList(getPhoto1DTO(), getPhoto2DTO(), getPhoto3DTO());
	}

	public static PhotoDTO getPhoto1DTO() {
		return PhotoDTO.builder().id("photo001").content(null).contentType("img/jpg").name("photo001").size(100)
				.build();
	}

	public static PhotoDTO getPhoto2DTO() {
		return PhotoDTO.builder().id("photo002").content(null).contentType("img/jpeg").name("photo002").size(100)
				.build();
	}

	public static PhotoDTO getPhoto3DTO() {
		return PhotoDTO.builder().id("photo003").content(null).contentType("img/png").name("photo003").size(100)
				.build();
	}
	
}
