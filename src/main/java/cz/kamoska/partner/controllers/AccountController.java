package cz.kamoska.partner.controllers;

import cz.kamoska.partner.beans.FakturoidDao;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.request.UpdateCompanyModel;
import cz.kamoska.partner.models.request.UpdateLoginModel;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import net.airtoy.encryption.MD5;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 16.11.12
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
@Model
public class AccountController {

	@Inject
	private Logger logger;
	@Inject
	private FacesContext facesContext;

	@Inject
	private UpdateLoginModel updateLoginModel;
	@Inject
	private UpdateCompanyModel updateCompanyModel;
	@Inject
	private LoggedInPartner loggedInPartner;
	@Inject
	private FacesMessageProvider facesMessageProvider;

	@EJB
	private PartnerDaoInterface partnerDaoInterface;
	@EJB
	private FakturoidDao fakturoidDao;

	public String updatePassword() {

		if (!loggedInPartner.getPartner().getPassword().equalsIgnoreCase(MD5.md5hexa(updateLoginModel.getOldPassword()))) {
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_INFO, facesMessageProvider.getLocalizedMessage("partner.edit.old-password.invalid"), facesContext);
		} else {
			if (updateLoginModel.getNewPassword() != null && !updateLoginModel.getNewPassword().isEmpty()) {
				// je zadano nove heslo
				loggedInPartner.getPartner().setPassword(MD5.md5hexa(updateLoginModel.getNewPassword()).toUpperCase());
			}
			loggedInPartner.getPartner().setEmail(updateLoginModel.getEmail());
			SaveDomainResult<PartnerEntity> update = partnerDaoInterface.update(loggedInPartner.getPartner());
			if (update.success) {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_INFO, facesMessageProvider.getLocalizedMessage("partner.edit.login.success"), facesContext);
				loggedInPartner.setPartner(update.item);
			} else {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_INFO, facesMessageProvider.getLocalizedMessage("partner.edit.login.failed"), facesContext);
				logger.warning("Can not change login credentials for partner " + loggedInPartner.getPartner());
			}
		}
		return null;
	}

	public String updateCompany() {

		if (loggedInPartner != null) {
			if (updateCompanyModel != null) {
				PartnerEntity partner = loggedInPartner.getPartner();
				partner.setCity(updateCompanyModel.getCity());
				partner.setCompany(updateCompanyModel.getCompany());
				partner.setDic(updateCompanyModel.getDic());
				partner.setIc(updateCompanyModel.getIc());
				partner.setPsc(updateCompanyModel.getPsc());
				partner.setPhoneNumber(updateCompanyModel.getPhone());
				partner.setStreet(updateCompanyModel.getStreet());
				partner.setVatPayer(updateCompanyModel.isVatPayer());

				if (fakturoidDao.updatePartner(partner)) {
					SaveDomainResult<PartnerEntity> update = partnerDaoInterface.update(partner);
					if (update.success) {
						loggedInPartner.setPartner(update.item);
						FacesMessageCreate.addMessage(FacesMessage.SEVERITY_INFO, facesMessageProvider.getLocalizedMessage("partner.edit.success"), facesContext);
					} else {
						logger.warning("Can not update partner");
						FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("partner.edit.error"), facesContext);
					}
				} else {
					logger.warning("Can not update partner " + partner + " in DB, because can not update data on fakturoid");
					FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("partner.edit.error"), facesContext);
				}

			}
		}

		return null;
	}

}
