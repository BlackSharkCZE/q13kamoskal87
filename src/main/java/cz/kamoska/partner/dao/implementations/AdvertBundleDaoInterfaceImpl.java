package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 13.10.12
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AdvertBundleDaoInterfaceImpl extends DaoTemplate<AdvertBundleEntity> implements AdvertBundleDaoInterface {

	@Override
	public List<AdvertBundleEntity> findAllForPartner(PartnerEntity partner) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("partnerID", partner.getId());
		return findListByNamedQuery("AdvertBundleEntity.findByPartnerId", 0, -1, params);
	}

	@Override
	public List<AdvertBundleEntity> findAllRequiredNewProforma() {
		return findListByNamedQuery("AdvertBundleEntity.native.findRequiredNewProforma", 0, -1, null);
	}

}
