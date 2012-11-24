package cz.kamoska.partner.models.dao;

import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 25.10.12
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
@Model
public class PartnerDaoModel {

	@EJB
	private PartnerDaoInterface partnerDaoInterface;

	public Long getPartnerCountOfActivatedPartners() {
		return partnerDaoInterface.getPartnerCountOfActivatedPartners();
	}

	public Long getPayingPartnerCount() {
		return partnerDaoInterface.getPayingPartnerCount();
	}

}
