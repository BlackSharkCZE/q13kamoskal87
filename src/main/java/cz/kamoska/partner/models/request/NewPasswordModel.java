package cz.kamoska.partner.models.request;

import javax.enterprise.inject.Model;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 19.11.12
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */
@Model
public class NewPasswordModel {

	private String email;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
