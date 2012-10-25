package cz.kamoska.partner.models.sessions;

import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.AdvertState;
import cz.kamoska.partner.models.template.AbstractModel;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 14.10.12
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
@Named
@SessionScoped
public class AdvertBundleModel extends AbstractModel<AdvertBundleEntity> implements Serializable {

	@EJB
	private AdvertBundleDaoInterface advertBundleDaoInterface;


	public AdvertBundleModel() {
		super(AdvertBundleEntity.class);
	}


	public List<AdvertBundleEntity> getAdvertBundlesForPartner(PartnerEntity partner) {
		List<AdvertBundleEntity> allForPartner = advertBundleDaoInterface.findAllForPartner(partner);
		for (AdvertBundleEntity ab : allForPartner) {
			if (ab.getAdvertEntityList()!=null && !ab.getAdvertEntityList().isEmpty()) {
				Iterator<AdvertEntity> iterator = ab.getAdvertEntityList().iterator();
				while (iterator.hasNext()) {
					AdvertEntity next = iterator.next();
					if (next.getState() == AdvertState.DELETED) {
						iterator.remove();
					}
				}
			}
		}
		return allForPartner;
	}

	@Override
	protected DaoInterface<AdvertBundleEntity> getBasicDaoInterface() {
		return advertBundleDaoInterface;
	}
}
