package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.enums.AdvertState;
import cz.kamoska.partner.support.ArrayStringUtils;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 20.10.12
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AdvertDaoInterfaceImpl extends DaoTemplate<AdvertEntity> implements AdvertDaoInterface {

	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

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
			logger.severe("Can not read Advert Count in state " + state);
			logger.throwing(this.getClass().getSimpleName(), "getAdvertCountInState", e);
		}
		return -1L;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AdvertEntity> findLessUsedBySection(String section, int count, List<Integer> excludeIDList) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("sectionUrlName", section);
		if (excludeIDList == null || excludeIDList.isEmpty()) {
			return findListByNamedQuery("AdvertEntity.native.findLessUsedBySection", 0, count, params);
		} else {
			final String query = LESS_USED_ADVERT_BY_SECTION_AND_EXLUDE_ID_LIST.replace("{@EXLUDE_ID_LIST@}", ArrayStringUtils.mkString(excludeIDList, ",", null));
			return em.createNativeQuery(query, AdvertEntity.class).setParameter("sectionUrlName", section).setFirstResult(0).setMaxResults(count).getResultList();
		}
	}

	private final static String LESS_USED_ADVERT_BY_SECTION_AND_EXLUDE_ID_LIST= "select a.id, a.accept_date, a.body, a.date_created, a.reject_date, a.reject_message, a.state, a.title, a.url, a.view_count, a.bundle_id, a.picture_id\n" +
		 "from advert a\n" +
		 "left join advert_accesslist_actual ac on a.id = ac.advert_id\n" +
		 "left join public.advert_bundle ab on ab.id = a.bundle_id\n" +
		 "left join public.advert_section asec on asec.advertentity_id = a.id\n" +
		 "left join public.section sec on sec.id = asec.sectionentitylist_id\n" +
		 "where ab.status = 'ACTIVE' and ab.valid_to > now() and a.state = 'ACTIVE'\n" +
		 "and (ac.datecreated > now() - '1 hour'::interval or ac.datecreated is null)\n" +
		 "and sec.url_name = #sectionUrlName\n" +
		 "and a.id not in ({@EXLUDE_ID_LIST@})  \n" +
		 "group by a.id, a.accept_date, a.body, a.date_created, a.reject_date, a.reject_message, a.state, a.title, a.url, a.view_count, a.bundle_id, a.picture_id\n" +
		 "order by count(a.id) asc";

}
