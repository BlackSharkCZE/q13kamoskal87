package cz.kamoska.partner.models.request;

import cz.kamoska.partner.models.sessions.LoggedInPartner;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 12.11.12
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
@Model
public class UpdateLoginModel implements Serializable {

	@Inject
	private LoggedInPartner loggedInPartner;

	private String email;
	private String oldPassword;
	private String newPassword;

	@PostConstruct
	public void postConstruct() {
		if (loggedInPartner.getPartner() != null) {
			email = loggedInPartner.getPartner().getEmail();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
