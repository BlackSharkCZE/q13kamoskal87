package cz.kamoska.partner.beans;

import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertAccessListActualInterface;
import cz.kamoska.partner.entities.AdvertAccessListEntity;
import cz.kamoska.partner.support.Kamoska;
import org.slf4j.Logger;

import javax.ejb.*;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 11.11.12
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AccessListBean {

	@Inject @Kamoska
	private Logger logger;

	@EJB
	private AdvertAccessListActualInterface advertAccessListActualInterface;


	@Asynchronous
	public void save(AdvertAccessListEntity aale) {
		SaveDomainResult<AdvertAccessListEntity> saveResult = advertAccessListActualInterface.save(aale);
		if (!saveResult.success) {
			logger.error("Can not save access record to DDB form " + aale);
		}
	}

}
