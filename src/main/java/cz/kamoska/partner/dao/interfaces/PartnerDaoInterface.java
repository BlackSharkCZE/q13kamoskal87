package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.entities.PartnerEntity;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public interface PartnerDaoInterface {

	/**
	 * Hleda partnera dle zadaneho emailu
	 * @param email
	 * @return
	 */
	PartnerEntity findByEmail(final String email);

}
