package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface AdvertPriceGroupDaoInterface extends DaoInterface<AdvertPriceGroupEntity> {

	/**
	 * Nalezne nasledujici advertPriceGroup pro zadaneho partnera. AdvertPriceGroup pro skupinu reklam zavysi na tom, kolik reklamnich skupin uz partner ma
	 * @param partnerEntity partner pro ktereho hledame cenovou skupinu
	 * @return nalezena cenova skupina
	 */
	AdvertPriceGroupEntity findNextForPartner(PartnerEntity partnerEntity);

}
