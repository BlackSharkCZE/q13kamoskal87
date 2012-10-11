package cz.kamoska.partner.controllers;

import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.PartnerGroups;
import cz.kamoska.partner.models.request.RegisterAccountModel;
import net.airtoy.encryption.MD5;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Calendar;

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
	private final Logger logger = Logger.getLogger(RegisterController.class);

	private final String REGISTER_SUCCESSFUL_OUTCOME = "register_successful";

	@Inject
	private RegisterAccountModel registerAccountModel;

	@EJB
	private PartnerDaoInterface partnerDao;


	public String registerNewAccount() {

		PartnerEntity partnerEntity = registerAccountModel.getPartnerEntity();
		if (partnerEntity != null) {
			partnerEntity.setRoles(Arrays.asList(new String[]{PartnerGroups.GROUP_PARTNER.toString()}));
			partnerEntity.setPassword(MD5.md5hexa(partnerEntity.getPassword()).toUpperCase());
		}

		SaveDomainResult<PartnerEntity> saveResult = partnerDao.save(partnerEntity);
		if (saveResult.success) {
			logger.info("Save new partner with ID:" + saveResult.item.getId());
			//todo odeslat konfirmacni e-mail
			return REGISTER_SUCCESSFUL_OUTCOME;
		} else {
			logger.info("Can not save Partner " + partnerEntity);
			//todo vyplnit hlasku, ze se registrace nezdarila
		}


		return null;
	}

}
