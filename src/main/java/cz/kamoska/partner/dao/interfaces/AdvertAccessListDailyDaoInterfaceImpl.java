package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertAccessListDailyEntity;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
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

	private final Logger logger = Logger.getLogger(AdvertAccessListDailyDaoInterfaceImpl.class);
	
	@Override
	public Long getDisplayCountByFromDateAndToDate(Date fromDate, Date toDate) {
		try {
			Long sum = (Long) em.createNamedQuery("AdvertAccessListDailyEntity.getDisplayCountByFromDateAndToDate")
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate).getSingleResult();
			em.flush();
			return sum;
		} catch (Exception e) {
			logger.error("Can not read advert display count by fromDate and toDate", e);
		}
		return -1L;
	}

}
