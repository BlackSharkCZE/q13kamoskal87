package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.PartnerGroups;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface PartnerDaoInterface extends DaoInterface<PartnerEntity> {

	/**
	 * Hleda partnera dle zadaneho emailu
	 * @param email
	 * @return
	 */
	PartnerEntity findByEmail(final String email);

	/**
	 * Vraci pocet partneru, kteri jsou ve skupine partnerGroup
	 * @param partnerGroups skupina pro kterou nas zajima pocet uzivatelelu
	 * @return pocet uzivatele v predane skupine
	 */
	Integer getPartnerCountByGroup(PartnerGroups partnerGroups);

	/**
	 * Vytvari view, ktere je nutne pro JAAS k overovani uzivatelu a nacitani jejich roli
	 * @return
	 */
	boolean createLoginRoleView();
}
