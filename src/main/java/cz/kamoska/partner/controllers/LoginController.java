package cz.kamoska.partner.controllers;

import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.request.LoginModel;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import net.airtoy.encryption.MD5;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.10.12
 * Time: 21:42
 * Controller pouzity pro prihlasovani do partnerskeho rozhrani
 */
@Named
@RequestScoped
public class LoginController implements Serializable {

	/* Nazvy OUTCOME do navigation.xml pro dany controller */
	private final String LOGIN_SUCCESSFUL_OUTCOME = "login_successful";
	private final String LOGIN_FAILED_OUTCOME = "login_failed";

	private final String LOGOUT_SUCCESSFUL_OUTCOME = "logout_successful";
	private final String LOGOUT_FAILED_OUTCOME = "logout_failed";


	private final Logger logger = Logger.getLogger(LoginController.class);

	@Inject
	private LoginModel loginModel;
	@Inject
	private LoggedInPartner loggedInPartner;

	@EJB
	private PartnerDaoInterface partnerDaoInterface;


	/**
	 * Odhlaseni uzivatele
	 * @return Nasledujci View po odhlaseni uzivateledo
	 */
	private String logout() {
		loggedInPartner.setLoggedInTimestamp(null);
		loggedInPartner.setPartner(null);
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try {
			request.logout();
			return LOGOUT_SUCCESSFUL_OUTCOME;
		} catch (Exception e) {
			logger.warn("Can not logout user: " + request.getUserPrincipal().getName());
			return LOGOUT_FAILED_OUTCOME;
		}
	}

	/**
	 * Pokusi se prihlasit uzivatele
	 * @return nasledujici View po prihalseni, nebo null pokud prihlaseni selhalo
	 */
	private String login() {

		PartnerEntity partnerByEmail = partnerDaoInterface.findByEmail(loginModel.getLogin());
		if (partnerByEmail.getActivated() != null) {

			final String currentPassword = MD5.md5(loginModel.getPassword()).toString();
			if (currentPassword.equals(partnerByEmail.getPassword())) {

				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				//musime zkontrolovat jestli uz nahodou neni nekdo prihlaseny. V takovem pripade ho odhlasime
				if (request.getUserPrincipal() != null) {
					try {
						request.logout();
					} catch (ServletException e) {
						logger.warn("Can not logout current user: " + request.getUserPrincipal().getName());
					} finally {
						loggedInPartner.setLoggedInTimestamp(null);
						loggedInPartner.setPartner(null);
					}
				}

				try {
					request.login(loginModel.getLogin(), loginModel.getPassword());
				} catch (ServletException e) {
					logger.info("Can not login partner "+loginModel.getLogin(), e);
					//todo nastavit chybovou zpravu, ze se prihlaseni nezdarilo (spatne udaje?)
					return null;
				}

				loggedInPartner.setPartner(partnerByEmail);
				loggedInPartner.setLoggedInTimestamp(Calendar.getInstance().getTime());
				return LOGIN_SUCCESSFUL_OUTCOME; // todo nastavit mapovani do pravidel pro akci na login_successful


			} else {
				// todo je zcela jiste chybne heslo, takze musime informovat uzivatele
			}
		} else {
			//todo navratit chybovou zpravu, ze ucet neni aktivovany
		}

		return LOGIN_FAILED_OUTCOME;

	}


}
