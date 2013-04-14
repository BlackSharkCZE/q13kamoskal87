package cz.kamoska.partner.controllers;

import cz.kamoska.partner.beans.singletons.MailerBean;
import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.request.NewPasswordModel;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import cz.kamoska.partner.support.FileTemplateLoader;
import cz.kamoska.partner.support.Kamoska;
import net.airtoy.encryption.MD5;
import org.apache.commons.lang.RandomStringUtils;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 19.11.12
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
@RequestScoped
@Named
public class NewPasswordController {

	@Inject @Kamoska
	private org.slf4j.Logger logger;
	@Inject
	private FacesContext facesContext;
	@Inject
	private FacesMessageProvider facesMessageProvider;
	@Inject
	private FileTemplateLoader fileTemplateLoader;
	@Inject
	private NewPasswordModel newPasswordModel;

	@EJB
	private MailerBean mailerBean;
	@EJB
	private PartnerDaoInterface partnerDaoInterface;

	public String sendNewPassword() {

		String nextView = null;

		if (newPasswordModel.getEmail() != null) {
			PartnerEntity partnerByEmail = partnerDaoInterface.findByEmail(newPasswordModel.getEmail());
			if (partnerByEmail != null) {
				String newPassword = RandomStringUtils.random(8, "abcdefghijklamnopgrstuvwxyz1234567890_+-ABCDEFGHIJKLAMNOPGRSTUVWXYZ");
				final String newPasswordHash = MD5.md5hexa(newPassword).toUpperCase();
				partnerByEmail.setPassword(newPasswordHash);
				SaveDomainResult<PartnerEntity> update = partnerDaoInterface.update(partnerByEmail);
				if (!update.success) {
					FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("new.password.set-failed"), facesContext);
				} else {
					logger.info("Change password for user " + partnerByEmail + " to: " + newPassword);
					final String body = fileTemplateLoader.loadFileFromResources("/new-password-email-template.html");
					if (body == null) {
						FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("new.password.sent-failed"), facesContext);
						logger.error("Can not load template for new-password-email-template. New password was not sent to e-mail " + partnerByEmail.getEmail());
					} else {
						String emailBody = body.replace("{PASSWORD}", newPassword).replace("{LOGIN}", partnerByEmail.getEmail());
						mailerBean.sendEmail(emailBody, MainConfig.EMAIL_NEW_PASSWORD_SUBJECT, Arrays.asList(new String[]{partnerByEmail.getEmail()}), MainConfig.EMAIL_FROM, null, true);
						nextView = "password-sent";
					}
				}
			} else {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_WARN, facesMessageProvider.getLocalizedMessage("new.password.invalid-email"), facesContext);
			}
		}
		return nextView;

	}

}
