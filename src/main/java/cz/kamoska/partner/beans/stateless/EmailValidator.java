package cz.kamoska.partner.beans.stateless;

import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 12.11.12
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */


@Stateless
@Local
public class EmailValidator{

	@EJB
	private PartnerDaoInterface partnerDaoInterface;

	public boolean isEmailUsed(final String email) {
		return partnerDaoInterface.findByEmail(email)!=null;
	}

}
