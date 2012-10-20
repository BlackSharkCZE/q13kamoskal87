package cz.kamoska.partner.models.sessions;

import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.template.AbstractModel;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
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
		return advertBundleDaoInterface.findAllForPartner(partner);
	}

	@Override
	protected DaoInterface<AdvertBundleEntity> getBasicDaoInterface() {
		return advertBundleDaoInterface;
	}
}
