package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:16
 * Objekt popisujici vlastniho partnera
 */
@Entity
@Table(name = "partner")
public class PartnerEntity {

	@Id
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "company")
	private String company;

	@NotNull
	@Column(name = "ic")
	private String ic;

	@NotNull
	@Column(name = "vat_payer")
	private Boolean vatPayer = false;

	@NotNull
	@Column(name = "street")
	private String street;

	@NotNull
	@Column(name = "city")
	private String city;

	@NotNull
	@Column(name = "psc")
	private String psc;

	@NotNull
	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToMany(mappedBy = "partnerEntity")
	private List<AdvertBundleEntity> advertBundleEntityList;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles")
	@Column(name = "role")
	private List<String> roles;



	public PartnerEntity() {
		dateCreated = Calendar.getInstance().getTime();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}
