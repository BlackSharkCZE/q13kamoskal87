package cz.kamoska.partner.models.request;

import cz.kamoska.partner.dao.interfaces.AdvertPriceGroupDaoInterface;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.models.sessions.LoggedInPartner;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 7.11.12
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class AdvertPriceGroupModel {

	@EJB
	private AdvertPriceGroupDaoInterface advertPriceGroupDaoInterface;

	@Inject
	private LoggedInPartner loggedInPartner;

	@Produces @RequestScoped
	@Named(value = "nextAPG")
	public AdvertPriceGroupEntity getNextAdvertPriceGroupForCurrentPartner() {

		return advertPriceGroupDaoInterface.findNextForPartner(loggedInPartner.getPartner());

	}

}
