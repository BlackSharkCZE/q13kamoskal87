package cz.kamoska.partner.controllers;

import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.interfaces.AdvertPriceGroupDaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import org.apache.log4j.Logger;

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

	private final Logger logger = Logger.getLogger(AdvertBundleController.class);

	@Inject
	private FacesMessageProvider facesMessageProvider;

	@EJB
	private AdvertPriceGroupDaoInterface advertPriceGroupDaoInterface;
	@EJB
	private AdvertBundleDaoInterface advertBundleDaoInterface;

	public String createEmptyAdvertBundle(PartnerEntity partnerEntity) {
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
			return "first-advert-bundle-created";
		} else {
			logger.error("Can not save Advert Bundle " + advertBundleEntity + " for partner " + partnerEntity);
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert-bundle.create.failed"), FacesContext.getCurrentInstance());
		}
		return null;
	}

	public void updateBundleName(AdvertBundleEntity advertBundleEntity) {
		SaveDomainResult<AdvertBundleEntity> update = advertBundleDaoInterface.update(advertBundleEntity);
		if (!update.success) {
			logger.error("Can not update AdvertBundle (Change Name): " + advertBundleEntity);
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_WARN,facesMessageProvider.getLocalizedMessage("bundle.change.name.failed").replace("{0}", advertBundleEntity.getName()), FacesContext.getCurrentInstance());
		}
	}

}
