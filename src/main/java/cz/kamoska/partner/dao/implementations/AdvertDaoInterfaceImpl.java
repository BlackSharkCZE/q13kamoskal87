package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.enums.AdvertState;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 20.10.12
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AdvertDaoInterfaceImpl extends DaoTemplate<AdvertEntity> implements AdvertDaoInterface {

	private final Logger logger = Logger.getLogger(AdvertDaoInterfaceImpl.class);

	@Override
	public List<AdvertEntity> findAllWaitingToAck() {
		return findListByNamedQuery("AdvertEntity.findAllWaitingToACK", 0, -1, null);
	}


	@Override
	public Long getAdvertCountInState(AdvertState state) {
		try {
			Long count = (Long) em.createNamedQuery("AdvertEntity.countInState").setParameter("state", state).getSingleResult();
			return count;
		} catch (Exception e) {
			logger.error("Can not read Advert Count in state " + state, e);
		}
		return -1L;
	}

	@Override
	public List<AdvertEntity> findLessUsedBySection(String section, int count) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("sectionUrlName", section);
		return findListByNamedQuery("AdvertEntity.native.findLessUsedBySection", 0, count, params);
	}
}
