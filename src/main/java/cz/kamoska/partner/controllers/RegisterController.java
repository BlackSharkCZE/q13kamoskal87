package cz.kamoska.partner.controllers;

import cz.kamoska.partner.beans.singletons.MailerBean;
import cz.kamoska.partner.beans.stateless.EmailValidator;
import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.PartnerGroups;
import cz.kamoska.partner.models.request.RegisterAccountModel;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import cz.kamoska.partner.support.FileTemplateLoader;
import cz.kamoska.partner.support.Kamoska;
import net.airtoy.ares.Address;
import net.airtoy.ares.AresUserInfo;
import net.airtoy.ares.services.AresICValidation;
import net.airtoy.encryption.MD5;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.10.12
 * Time: 22:15
 * Controller, ktery zajistuje registraci noveho partnera
 */
@Named
@RequestScoped
public class RegisterController {
	@Inject @Kamoska
private org.slf4j.Logger logger;

	private final String REGISTER_SUCCESSFUL_OUTCOME = "register_successful";

	@Inject
	private RegisterAccountModel registerAccountModel;
	@Inject
	private FacesMessageProvider facesMessageProvider;

	@Inject
	private FileTemplateLoader fileTemplateLoader;

	@EJB
	private PartnerDaoInterface partnerDao;
	@EJB
	private MailerBean mailerBean;
	@EJB
	private EmailValidator emailValidator;


	public String checkIcoInAres() {


		if (registerAccountModel.getPartnerEntity().getIc() != null) {
			AresICValidation validator = new AresICValidation();
			AresUserInfo aresUser = validator.getUser(registerAccountModel.getPartnerEntity().getIc());
			if (aresUser != null) {
				registerAccountModel.getPartnerEntity().setCompany(aresUser.getCompany());
				Address address = aresUser.getAddress();
				registerAccountModel.getPartnerEntity().setStreet(address.getStreet() + (address.getCo() != null ? " " + address.getCo() : ""));
				registerAccountModel.getPartnerEntity().setCity(address.getCity());
				registerAccountModel.getPartnerEntity().setPsc(address.getPsc());
			} else {
				registerAccountModel.getPartnerEntity().setCompany("");
				registerAccountModel.getPartnerEntity().setStreet("");
				registerAccountModel.getPartnerEntity().setCity("");
				registerAccountModel.getPartnerEntity().setPsc("");
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_WARN, facesMessageProvider.getLocalizedMessage("register.ico.not-found"),FacesContext.getCurrentInstance());

			}

		}

		return null;

	}

	public String registerNewAccount() {
		String textPassword = null;

		PartnerEntity partnerEntity = registerAccountModel.getPartnerEntity();
		if (partnerEntity != null) {

			if (registerAccountModel.isEulaAgreement()) {
				if (!emailValidator.isEmailUsed(partnerEntity.getEmail())) {
					textPassword = partnerEntity.getPassword();
					partnerEntity.setRoles(Arrays.asList(PartnerGroups.GROUP_PARTNER.toString()));
					partnerEntity.setPassword(MD5.md5hexa(partnerEntity.getPassword()).toUpperCase());

					SaveDomainResult<PartnerEntity> saveResult = partnerDao.save(partnerEntity);
					if (saveResult.success) {
						logger.info("Save new partner with ID:" + saveResult.item.getId());
						sendConfirmationEmail(saveResult.item, textPassword);

						Path path = FileSystems.getDefault().getPath(MainConfig.IMAGE_STORE_ROOT_PATH,saveResult.item.getId()+"/src");
						try {
							Files.createDirectories(path);
						} catch (IOException e) {
							logger.error("Can not create image directory for new partner " + partnerEntity + ". Create file MANUALLY!");
						}

						return REGISTER_SUCCESSFUL_OUTCOME;
					} else {
						logger.error("Can not save Partner " + partnerEntity);
						FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("registration.error"), FacesContext.getCurrentInstance());
					}
				} else {
					logger.warn("Email " + partnerEntity.getEmail() + " already used!");
					FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("registration.email.used"), FacesContext.getCurrentInstance());
				}
			} else {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("eula-agreement"), FacesContext.getCurrentInstance());
			}


		} else {
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("registration.error"), FacesContext.getCurrentInstance());
			logger.error("Can not save Partner because partnerEntity is null ");
		}

		return null;
	}

	private void sendConfirmationEmail(PartnerEntity partnerEntity, final String textPassword) {
		final String template = loadEmailTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuilder confirmUrl = new StringBuilder(256);
		confirmUrl.append(request.getProtocol().startsWith("HTTPS") ? "https://" : "http://");
		confirmUrl.append(request.getServerName());
		if (request.getServerPort() != 80 && request.getServerPort()!=443) {
			confirmUrl.append(":").append(request.getServerPort());
		}
		confirmUrl.append(request.getContextPath());
		confirmUrl.append("/activate/").append(partnerEntity.getEmailConfirmationHash());

		final String body = template.replace("{LOGIN}", partnerEntity.getEmail()).replace("{PASSWORD}", textPassword).replace("{CONFIRM_URL}", confirmUrl);
		mailerBean.sendEmail(body, MainConfig.EMAIL_REGISTER_SUBJECT, Arrays.asList(new String[] {partnerEntity.getEmail()}), MainConfig.EMAIL_FROM,null, true);

	}

	private String loadEmailTemplate() {
		return fileTemplateLoader.loadFileFromResources("/registration-email-template.html");
	}

}
