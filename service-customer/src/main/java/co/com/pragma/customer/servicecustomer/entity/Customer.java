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


import co.com.pragma.customer.servicecustomer.model.PhotoDTO;
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
	private Long id;

	@ManyToOne
	@JoinColumn(name = "identification_type_id")
	private IdentificationType identificationType;

	private String identification;

	private String name;

	@Column(name = "last_name")
	private String lastName;

	private Integer age;

	@ManyToOne
	@JoinColumn(name = "city_of_birth_id")
	private City cityOfBirth;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@Column(name = "update_at")
	@Temporal(TemporalType.DATE)
	private Date updateAt;

	@Column(name = "photo_id")
	private String photoId;


}
