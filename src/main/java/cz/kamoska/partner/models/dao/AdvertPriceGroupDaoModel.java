package cz.kamoska.partner.models.dao;

import cz.kamoska.partner.dao.interfaces.AdvertPriceGroupDaoInterface;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 13.10.12
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
@Named()
@RequestScoped
public class AdvertPriceGroupDaoModel {

	@EJB
	private AdvertPriceGroupDaoInterface advertPriceGroupDaoInterface;

	public List<AdvertPriceGroupEntity> getAllItems() {
		return advertPriceGroupDaoInterface.getAll();
	}

}
