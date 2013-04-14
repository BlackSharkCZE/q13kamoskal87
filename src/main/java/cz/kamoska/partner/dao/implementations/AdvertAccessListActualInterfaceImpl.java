package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.AdvertAccessListActualInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertAccessListEntity;
import cz.kamoska.partner.support.Kamoska;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 11.11.12
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AdvertAccessListActualInterfaceImpl extends DaoTemplate<AdvertAccessListEntity> implements AdvertAccessListActualInterface {

	@Inject
	@Kamoska
	private org.slf4j.Logger logger;

	@Override
	public Long getDisplayCountByFromDateAndToDate(Date fromDate, Date toDate) {
		try {
			Long res = (Long) em.createNamedQuery("AdvertAccessListEntity.getDisplayCountByFromDateAndToDate")
				 .setParameter("fromDate", fromDate)
				 .setParameter("toDate", toDate).getSingleResult();
			return res == null ? 0 : res;
		} catch (Exception e) {
			logger.error("Can not read count of advert display by from date and to date", e);
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
			logger.error("Can not read count of advert display by from date and to date and partnerID " + partnerID, e);
		}
		return -1L;

	}

}
