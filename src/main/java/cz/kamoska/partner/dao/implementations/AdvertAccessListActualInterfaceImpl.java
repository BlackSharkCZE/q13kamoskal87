package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.AdvertAccessListActualInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertAccessListEntity;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
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

	private final Logger logger = Logger.getLogger(AdvertAccessListActualInterfaceImpl.class);

	@Override
	public Long getDisplayCountByFromDateAndToDate(Date fromDate, Date toDate) {
		try {
			Long res = (Long) em.createNamedQuery("AdvertAccessListEntity.getDisplayCountByFromDateAndToDate")
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate).getSingleResult();
			return res;
		} catch (Exception e) {
			logger.error("Can not read count of advert display by from date and to date", e);
		}
		return -1L;
	}

}
