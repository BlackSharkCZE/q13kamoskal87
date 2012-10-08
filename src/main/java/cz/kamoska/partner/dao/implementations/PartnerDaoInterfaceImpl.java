package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Stateless;

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
	public PartnerEntity findByEmail(String email) {
		//todo implement body
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
