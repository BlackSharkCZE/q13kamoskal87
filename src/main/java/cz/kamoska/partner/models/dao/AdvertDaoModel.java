package cz.kamoska.partner.models.dao;

import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.enums.AdvertState;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 24.10.12
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
@Model
@Named
public class AdvertDaoModel {

	@EJB
	private AdvertDaoInterface advertDaoInterface;


	public List<AdvertEntity> getAllWaitingToAck() {
		return advertDaoInterface.findAllWaitingToAck();
	}

	public Long getCountInState(AdvertState state) {
		return advertDaoInterface.getAdvertCountInState(state);
	}

}
