package cz.kamoska.partner.models.sessions;

import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.enums.AdvertState;
import cz.kamoska.partner.models.template.AbstractModel;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
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

	@Inject
	private LoggedInPartner loggedInPartner;

	private boolean allPaid = true;

	public AdvertBundleModel() {
		super(AdvertBundleEntity.class);
	}

	public List<AdvertBundleEntity> getAdvertBundlesForPartner() {
		List<AdvertBundleEntity> allForPartner = advertBundleDaoInterface.findAllForPartner(loggedInPartner.getPartner());
		allPaid = true;

		for (AdvertBundleEntity ab : allForPartner) {

			if (allPaid) {
				allPaid = ab.getStatus() == AdvertState.ACTIVE && ab.getAdvertCount() == 5;
			}

			if (ab.getAdvertEntityList() != null && !ab.getAdvertEntityList().isEmpty()) {
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

	public boolean isAllPaid() {
		return allPaid;
	}

	public void setAllPaid(boolean allPaid) {
		this.allPaid = allPaid;
	}

	@Override
	protected DaoInterface<AdvertBundleEntity> getBasicDaoInterface() {
		return advertBundleDaoInterface;
	}
}
