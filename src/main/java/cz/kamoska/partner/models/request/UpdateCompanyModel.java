package cz.kamoska.partner.models.request;

import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 12.11.12
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
@Model
public class UpdateCompanyModel {
	@Inject
	private LoggedInPartner loggedInPartner;

	private String company;
	private String ic;
	private String dic;
	private boolean vatPayer;
	private String street;
	private String city;
	private String psc;
	private String phone;


	@PostConstruct
	public void postConstruct() {

		if (loggedInPartner.getPartner() != null) {
			PartnerEntity p = loggedInPartner.getPartner();
			company = p.getCompany();
			ic = p.getIc();
			dic = p.getDic();
			vatPayer = p.getVatPayer();
			street = p.getStreet();
			city = p.getCity();
			psc = p.getPsc();
			phone = p.getPhoneNumber();
		}

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDic() {
		return dic;
	}

	public void setDic(String dic) {
		this.dic = dic;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public LoggedInPartner getLoggedInPartner() {
		return loggedInPartner;
	}

	public void setLoggedInPartner(LoggedInPartner loggedInPartner) {
		this.loggedInPartner = loggedInPartner;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPsc() {
		return psc;
	}

	public void setPsc(String psc) {
		this.psc = psc;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public boolean isVatPayer() {
		return vatPayer;
	}

	public void setVatPayer(boolean vatPayer) {
		this.vatPayer = vatPayer;
	}
}
