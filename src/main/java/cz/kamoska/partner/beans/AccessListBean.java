package cz.kamoska.partner.beans;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertAccessListActualInterface;
import cz.kamoska.partner.entities.AdvertAccessListEntity;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 11.11.12
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AccessListBean {

	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

	@EJB
	private AdvertAccessListActualInterface advertAccessListActualInterface;


	@Asynchronous
	public void save(AdvertAccessListEntity aale) {
		SaveDomainResult<AdvertAccessListEntity> saveResult = advertAccessListActualInterface.save(aale);
		if (!saveResult.success) {
			logger.severe("Can not save access record to DDB form " + aale);
		}
	}

}
