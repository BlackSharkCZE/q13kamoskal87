package cz.kamoska.partner.beans.stateless;

import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;

import javax.ejb.*;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 12.11.12
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */


@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)

public class EmailValidator{

	@EJB
	private PartnerDaoInterface partnerDaoInterface;

	public boolean isEmailUsed(final String email) {
		return partnerDaoInterface.findByEmail(email)!=null;
	}

}
