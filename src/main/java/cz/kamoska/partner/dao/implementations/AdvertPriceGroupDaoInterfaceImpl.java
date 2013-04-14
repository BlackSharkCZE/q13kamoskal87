package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.AdvertPriceGroupDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 13.10.12
 * Time: 13:54
 *
 */
@Stateless
public class AdvertPriceGroupDaoInterfaceImpl extends DaoTemplate<AdvertPriceGroupEntity> implements AdvertPriceGroupDaoInterface {

	@Override
	public List<AdvertPriceGroupEntity> getAll() {
		return findListByNamedQuery("AdvertPriceGroupEntity.findAll", 0, -1, null);
	}

	@Override
	public AdvertPriceGroupEntity findNextForPartner(PartnerEntity partnerEntity) {
		Map<String, Object> params = new HashMap<>(1);
		List<AdvertBundleEntity> abl = partnerEntity.getAdvertBundleEntityList();
		Integer oindex = abl != null ? abl.size() : 0;
		params.put("oindex", (oindex+1));
		AdvertPriceGroupEntity singleByNamedQuery = findSingleByNamedQuery("AdvertPriceGroupEntity.findNextForOIndex", params);
		return singleByNamedQuery;
	}
}
