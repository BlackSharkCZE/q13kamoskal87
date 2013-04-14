package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertAccessListDailyEntity;
import cz.kamoska.partner.support.Kamoska;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 24.11.12
 * Time: 20:47
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AdvertAccessListDailyDaoInterfaceImpl extends DaoTemplate<AdvertAccessListDailyEntity> implements AdvertAccessListDailyDaoInterface {

	@Inject
	@Kamoska
	private org.slf4j.Logger logger;

	@Override
	public Long getDisplayCountByFromDateAndToDate(Date fromDate, Date toDate) {
		try {
			Long sum = (Long) em.createNamedQuery("AdvertAccessListDailyEntity.getDisplayCountByFromDateAndToDate")
				 .setParameter("fromDate", fromDate)
				 .setParameter("toDate", toDate).getSingleResult();
			em.flush();
			return sum == null ? 0 : sum;
		} catch (Exception e) {
			logger.error("Can not read advert display count by fromDate and toDate", e);
		}
		return -1L;
	}

	@Override
	public Long getDisplayCountByFromDateAndToDateAndPartnerID(Date fromDate, Date toDate, Integer partnerID) {

		try {
			Long sum = (Long) em.createNamedQuery("AdvertAccessListDailyEntity.native.getDisplatCoutByFromDateAndToDateAndPartnerID")
				 .setParameter("fromDate", fromDate)
				 .setParameter("toDate", toDate)
				 .setParameter("partnerID", partnerID).getSingleResult();
			em.flush();
			return sum == null ? 0 : sum;
		} catch (Exception e) {
			logger.error("Can not read advert display count by fromDate and toDate and partnerID " + partnerID, e);
		}
		return -1L;

	}

}
