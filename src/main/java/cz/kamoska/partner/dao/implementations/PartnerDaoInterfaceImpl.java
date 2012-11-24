package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.PartnerGroups;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 8.10.12
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class PartnerDaoInterfaceImpl extends DaoTemplate<PartnerEntity> implements PartnerDaoInterface {


	@Override
	public Integer getPartnerCountByGroup(PartnerGroups partnerGroups) {
		try {
			Query query = em.createNamedQuery("PartnerEntity.findCountByGroup").setParameter("group", PartnerGroups.GROUP_ADMIN.toString());
			Long count = (Long) query.getSingleResult();
			return count.intValue();
		} catch (Exception e) {
			logger.info("Can not obtain partner count in role " + PartnerGroups.GROUP_ADMIN, e);
		}
		return 0;
	}

	@Override
	public boolean createLoginRoleView() {
		Integer res = executeUpdateNamedQuery("PartnerEntity.createRoleView", null);
		return res > 0;
	}

	@Override
	public PartnerEntity findByEmailConfirmationHash(String emailConfirmationHash) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("cHash", emailConfirmationHash);
		return findSingleByNamedQuery("PartnerEntity.findByConfirmationEmail", params);
	}

	@Override
	public Long getPartnerCountOfActivatedPartners() {
		try {
			return (Long)em.createNamedQuery("PartnerEntity.countOfAllActivatedPartners").getSingleResult();
		} catch (Exception e) {
			logger.error("Can not obtain count of activated partners", e);
		}
		return -1L;
	}

	@Override
	public Long getPayingPartnerCount() {
		try {
			Long res = (Long) em.createNamedQuery("PartnerEntity.native.getCountOfPaying").getSingleResult();
			em.flush();
			return res;
		} catch (Exception e) {
			logger.error("Can not read count of paying partners.", e);
		}
		return -1L;
	}

	@Override
	public PartnerEntity findByEmail(String email) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("email", email);
		return findSingleByNamedQuery("PartnerEntity.findByEmail", params);
	}
}
