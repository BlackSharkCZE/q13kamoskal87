package cz.kamoska.partner.controllers;

import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.request.LoginModel;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import net.airtoy.encryption.MD5;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
	private final String LOGIN_FAILED_OUTCOME = null;

	private final String LOGOUT_SUCCESSFUL_OUTCOME = "logout_successful";
	private final String LOGOUT_FAILED_OUTCOME = "logout_failed";


	private final Logger logger = Logger.getLogger(LoginController.class);

	@Inject
	private LoginModel loginModel;
	@Inject
	private LoggedInPartner loggedInPartner;
	@Inject
	private FacesMessageProvider facesMessageProvider;

	@EJB
	private PartnerDaoInterface partnerDaoInterface;


	/**
	 * Odhlaseni uzivatele
	 * @return Nasledujci View po odhlaseni uzivateledo
	 */
	public String logout() {
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
	public String login() {

		PartnerEntity partnerByEmail = partnerDaoInterface.findByEmail(loginModel.getLogin());
		if (partnerByEmail != null && partnerByEmail.getActivated() != null) {

			final String currentPassword = MD5.md5hexa(loginModel.getPassword()).toUpperCase();
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
					FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("login.error.invalid-password"), FacesContext.getCurrentInstance());
					return null;
				}
				loggedInPartner.setPartner(partnerByEmail);
				loggedInPartner.setLoggedInTimestamp(Calendar.getInstance().getTime());
				return LOGIN_SUCCESSFUL_OUTCOME;
			} else {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("login.error.invalid-password"), FacesContext.getCurrentInstance());
			}
		} else {
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("login.error.account-not-activated"), FacesContext.getCurrentInstance());
		}
		return LOGIN_FAILED_OUTCOME;

	}


}
