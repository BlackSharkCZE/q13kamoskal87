package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface AdvertBundleDaoInterface extends DaoInterface<AdvertBundleEntity> {

	List<AdvertBundleEntity> findAllForPartner(PartnerEntity partner);

	/**
	 * Hleda vsechny AdvertBundle, ke kterym je treba vystavit novou proforma-fakturu
	 * @return
	 */
	List<AdvertBundleEntity> findAllRequiredNewProforma();

}
