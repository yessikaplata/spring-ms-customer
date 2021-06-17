package co.com.pragma.customer.servicecustomer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@NotNull (message = "Identification Type must not be null.")
	@ManyToOne
	@JoinColumn(name = "identification_type_id")
	private IdentificationType identificationType;

	@NotEmpty (message = "Identification must not be empty.")
	private String identification;


	@NotEmpty (message = "Name must not be empty.")
	private String name;

	@NotEmpty (message = "Lastname must not be empty.")
	@Column(name = "last_name")
	private String lastName;

	@NotNull (message = "Age must not be null.")
	private Integer age;

	@NotNull (message = "City of birth must not be null.")
	@ManyToOne
	@JoinColumn(name = "city_of_birth_id")
	private City cityOfBirth;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@Column(name = "update_at")
	@Temporal(TemporalType.DATE)
	private Date updateAt;
	
	@Transient
	private String photoBase64;

}
