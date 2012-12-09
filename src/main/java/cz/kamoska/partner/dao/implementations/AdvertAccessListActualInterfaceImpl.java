package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.interfaces.AdvertAccessListActualInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertAccessListEntity;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 11.11.12
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AdvertAccessListActualInterfaceImpl extends DaoTemplate<AdvertAccessListEntity> implements AdvertAccessListActualInterface {

	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

	@Override
	public Long getDisplayCountByFromDateAndToDate(Date fromDate, Date toDate) {
		try {
			Long res = (Long) em.createNamedQuery("AdvertAccessListEntity.getDisplayCountByFromDateAndToDate")
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate).getSingleResult();
			return res == null ? 0 : res;
		} catch (Exception e) {
			logger.severe("Can not read count of advert display by from date and to date");
			logger.throwing(this.getClass().getSimpleName(), "getDisplayCountByFromDateAndToDate", e);
		}
		return -1L;
	}

	@Override
	public Long getDisplayCountByFromDateAndToDateAndPartnerID(Date fromDate, Date toDate, Integer partnerID) {

		try {
			Long res = (Long) em.createNamedQuery("AdvertAccessListEntity.native.getDisplayCountByFromDateAndToDateAndPartnerID")
				 .setParameter("fromDate", fromDate)
				 .setParameter("toDate", toDate)
				 .setParameter("partnerID", partnerID).getSingleResult();
			return res == null ? 0 : res;
		} catch (Exception e) {
			logger.severe("Can not read count of advert display by from date and to date and partnerID " + partnerID);
			logger.throwing(this.getClass().getSimpleName(), "getDisplayCountByFromDateAndToDate", e);
		}
		return -1L;

	}

}
