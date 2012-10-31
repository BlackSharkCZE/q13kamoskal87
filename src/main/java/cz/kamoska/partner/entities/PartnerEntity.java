package cz.kamoska.partner.entities;

import net.airtoy.encryption.MD5;

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

@NamedQueries({
		@NamedQuery(name = "PartnerEntity.findCountByGroup", query = "SELECT count(p) FROM PartnerEntity p WHERE :group MEMBER OF p.roles"),
		@NamedQuery(name = "PartnerEntity.findByConfirmationEmail", query = "SELECT p FROM PartnerEntity p WHERE p.emailConfirmationHash = :cHash"),
		@NamedQuery(name = "PartnerEntity.findByEmail", query = "SELECT p FROM PartnerEntity p WHERE p.email = :email"),
		@NamedQuery(name = "PartnerEntity.countOfAllActivatedPartners", query = "SELECT COUNT(p) FROM PartnerEntity p WHERE p.roles = 'GROUP_PARTNER' AND p.activated IS NOT null")
})

@NamedNativeQueries({
		@NamedNativeQuery(name = "PartnerEntity.createRoleView", query = "create or replace  view v_partner_role_cross as select p.email as login, r.role as role  from partner p join roles r on r.partnerentity_id = p.id")
})

@Entity
@Table(name = "partner")
public class PartnerEntity {

	@Id
	@GeneratedValue(generator = "partnerIDGenerator")
	@SequenceGenerator(allocationSize = 1, sequenceName = "partner_id_seq", name = "partnerIDGenerator")
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

	@Column(name = "dic")
	private String dic;

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

	@Column(name = "activated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activated;

	@NotNull
	@Column(name = "email_confirmation_hash", length = 64)
	private String emailConfirmationHash;


	@Column(name = "fakturoid_id")
	private Integer fakturoidId;

	@PrePersist
	public void prePersist() {
		dateCreated = Calendar.getInstance().getTime();
		emailConfirmationHash = MD5.md5hexa(email + ":" + dateCreated.getTime());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PartnerEntity that = (PartnerEntity) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public PartnerEntity() {
		dateCreated = Calendar.getInstance().getTime();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public Boolean getVatPayer() {
		return vatPayer;
	}

	public void setVatPayer(Boolean vatPayer) {
		this.vatPayer = vatPayer;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPsc() {
		return psc;
	}

	public void setPsc(String psc) {
		this.psc = psc;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<AdvertBundleEntity> getAdvertBundleEntityList() {
		return advertBundleEntityList;
	}

	public void setAdvertBundleEntityList(List<AdvertBundleEntity> advertBundleEntityList) {
		this.advertBundleEntityList = advertBundleEntityList;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getActivated() {
		return activated;
	}

	public void setActivated(Date activated) {
		this.activated = activated;
	}

	public String getDic() {
		return dic;
	}

	public void setDic(String dic) {
		this.dic = dic;
	}

	public String getEmailConfirmationHash() {
		return emailConfirmationHash;
	}

	public void setEmailConfirmationHash(String emailConfirmationHash) {
		this.emailConfirmationHash = emailConfirmationHash;
	}

	public Integer getFakturoidId() {
		return fakturoidId;
	}

	public void setFakturoidId(Integer fakturoidId) {
		this.fakturoidId = fakturoidId;
	}
}
