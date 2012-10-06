package cz.kamoska.partner.models.sessions;

import cz.kamoska.partner.entities.PartnerEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.10.12
 * Time: 21:46
 * Udrzuje v Session aktualne prihlaseneho partnera.
 */
@Named
@SessionScoped
public class LoggedInPartner implements Serializable {

	private Date loggedInTimestamp; // cas kdy byl uzivatel prihlaseny
	private PartnerEntity partner; // aktualne prihlaseny partner

	public PartnerEntity getPartner() {
		return partner;
	}

	public void setPartner(PartnerEntity partner) {
		this.partner = partner;
	}

	public Date getLoggedInTimestamp() {
		return loggedInTimestamp;
	}

	public void setLoggedInTimestamp(Date loggedInTimestamp) {
		this.loggedInTimestamp = loggedInTimestamp;
	}
}
