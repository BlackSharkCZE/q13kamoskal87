package cz.kamoska.partner.pojo.fakturoid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.kamoska.partner.entities.PartnerEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 27.10.12
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public class Subject {

	private Integer id;
	private String name;
	private String street;
	private String street2;
	private String city;
	private String zip;
	private String country;
	private String email;
	private String iban;
	private String phone;
	private String web;
	private String url;


	private Integer customId;
	private String registrationNo;
	private String vatNo;
	private String bankAccount;
	private String fullName;
	private String emailCopy;
	private String htmlUrl;
	private Date updateAt;


	public Subject() {
	}

	public Subject(PartnerEntity partnerEntity) {

		name = partnerEntity.getCompany();
		street = partnerEntity.getStreet();
		city = partnerEntity.getCity();
		zip = partnerEntity.getPsc();
		email = partnerEntity.getEmail();
		phone = partnerEntity.getPhoneNumber();
		customId = partnerEntity.getId();
		registrationNo = partnerEntity.getIc();
		if (vatNo != null) {
			vatNo = partnerEntity.getDic();
		}

	}

	@JsonProperty(value = "custom_id")
	public Integer getCustomId() {
		return customId;
	}

	public void setCustomId(Integer customId) {
		this.customId = customId;
	}

	@JsonProperty(value = "registration_no")
	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}


	@JsonProperty(value = "vat_no")
	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	@JsonProperty(value = "bank_account")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@JsonProperty(value = "full_name")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonProperty(value = "email_copy")
	public String getEmailCopy() {
		return emailCopy;
	}

	public void setEmailCopy(String emailCopy) {
		this.emailCopy = emailCopy;
	}

	@JsonProperty(value = "html_url", required = false)
	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	@JsonProperty(value = "updated_at")
	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}


	@JsonIgnore
	public Integer getId() {
		return id;
	}

	@JsonProperty
	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public String toString() {
		return "Subject{" +
				"id=" + id +
				", name='" + name + '\'' +
				", street='" + street + '\'' +
				", street2='" + street2 + '\'' +
				", city='" + city + '\'' +
				", zip='" + zip + '\'' +
				", country='" + country + '\'' +
				", email='" + email + '\'' +
				", iban='" + iban + '\'' +
				", phone='" + phone + '\'' +
				", web='" + web + '\'' +
				", url='" + url + '\'' +
				", customId=" + customId +
				", registrationNo='" + registrationNo + '\'' +
				", vatNo='" + vatNo + '\'' +
				", bankAccount='" + bankAccount + '\'' +
				", fullName='" + fullName + '\'' +
				", emailCopy='" + emailCopy + '\'' +
				", htmlUrl='" + htmlUrl + '\'' +
				", updateAt=" + updateAt +
				'}';
	}
}
