package cz.kamoska.partner.controllers;

import cz.kamoska.partner.beans.FakturoidDao;
import cz.kamoska.partner.beans.singletons.DefaultMessages;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.interfaces.AdvertPriceGroupDaoInterface;
import cz.kamoska.partner.dao.interfaces.MessageDaoInterface;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.MessageEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.sessions.AdvertBundleModel;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.pojo.kamoska.DefaultMessage;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import cz.kamoska.partner.support.Kamoska;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 13.10.12
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
@RequestScoped
@Named
public class AdvertBundleController implements Serializable {

	@Inject
	@Kamoska
	private org.slf4j.Logger logger;

	@Inject
	private FacesMessageProvider facesMessageProvider;

	@Inject
	private LoggedInPartner loggedInPartner;
	@Inject
	private DefaultMessages defaultMessages;
	@Inject
	private AdvertBundleModel advertBundleModel;

	@EJB
	private AdvertPriceGroupDaoInterface advertPriceGroupDaoInterface;
	@EJB
	private AdvertBundleDaoInterface advertBundleDaoInterface;
	@EJB
	private FakturoidDao fakturoidDao;
	@EJB
	private PartnerDaoInterface partnerDaoInterface;

	@EJB
	private MessageDaoInterface messageDaoInterface;


	public String createEmptyAdvertBundle(PartnerEntity partnerEntity) {

		logger.info("Try register partner into fakturoid");
		Integer fId = null;
		if (partnerEntity.getFakturoidId() == null) {
			fId = fakturoidDao.registerPartner(partnerEntity);
			partnerEntity.setFakturoidId(fId);
		} else {
			fId = partnerEntity.getFakturoidId();
		}

		if (fId == null) {
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("fakturoid.register-partner.failed"), FacesContext.getCurrentInstance());
		} else {
			SaveDomainResult<PartnerEntity> updateResult = partnerDaoInterface.update(partnerEntity);
			if (!updateResult.success) {
				logger.error("Can not assign Fakturoid_ID to partner " + partnerEntity);
			} else {
				loggedInPartner.setPartner(updateResult.item);
				partnerEntity = updateResult.item;
			}

			if (partnerEntity.getAdvertBundleEntityList() == null) {
				partnerEntity.setAdvertBundleEntityList(new ArrayList<AdvertBundleEntity>(5));
			}

			final String name = facesMessageProvider.getLocalizedMessage("default-advert-bundle-name").replace("{0}", String.valueOf(partnerEntity.getAdvertBundleEntityList().size() + 1));
			AdvertBundleEntity advertBundleEntity = new AdvertBundleEntity();
			advertBundleEntity.setDateCreated(Calendar.getInstance().getTime());
			advertBundleEntity.setName(name);
			advertBundleEntity.setPartnerEntity(partnerEntity);
			advertBundleEntity.setAdvertPriceGroupEntity(advertPriceGroupDaoInterface.findNextForPartner(partnerEntity));

			SaveDomainResult<AdvertBundleEntity> saveResult = advertBundleDaoInterface.save(advertBundleEntity);
			if (saveResult.success) {
				logger.info("Advert bundle " + saveResult.item + " added to partner " + partnerEntity);
				partnerEntity.getAdvertBundleEntityList().add(saveResult.item);


				DefaultMessage message = defaultMessages.getMessageFor(DefaultMessages.MessageCategory.FIRST_MESSAGE_BUNDLE);
				message.replaceAll("{BUNDLE_NAME}", saveResult.item.getName());

				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setBody(message.getBody());
				messageEntity.setTitle(message.getTitle());
				messageEntity.setMessageType(message.getMessageType());
				messageEntity.setGroupUid(String.valueOf(Calendar.getInstance().getTime().getTime()));
				messageEntity.setPartner(loggedInPartner.getPartner());
				messageEntity.setPublishDate(Calendar.getInstance().getTime());

				SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
				if (!messageSaveResult.success) {
					logger.warn("Can not save system message " + messageEntity);
				} else {
					logger.info("Message " + messageEntity + " saved");
				}

				advertBundleModel.setAllPaid(false);

				return "first-advert-bundle-created";
			} else {
				logger.error("Can not save Advert Bundle " + advertBundleEntity + " for partner " + partnerEntity);
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert-bundle.create.failed"), FacesContext.getCurrentInstance());
			}
		}
		return null;

	}

	public void updateBundleName(AdvertBundleEntity advertBundleEntity) {
		SaveDomainResult<AdvertBundleEntity> update = advertBundleDaoInterface.update(advertBundleEntity);
		if (!update.success) {
			logger.error("Can not update AdvertBundle (Change Name): " + advertBundleEntity);
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_WARN, facesMessageProvider.getLocalizedMessage("bundle.change.name.failed").replace("{0}", advertBundleEntity.getName()), FacesContext.getCurrentInstance());
		}
	}

}
