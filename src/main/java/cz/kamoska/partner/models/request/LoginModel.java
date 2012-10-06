package cz.kamoska.partner.models.request;

import javax.enterprise.inject.Model;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.10.12
 * Time: 21:43
 * Nese informace zadane v prihlasovacim formulari - platne pro request
 */
@Model
public class LoginModel implements Serializable {

	private String login;
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
