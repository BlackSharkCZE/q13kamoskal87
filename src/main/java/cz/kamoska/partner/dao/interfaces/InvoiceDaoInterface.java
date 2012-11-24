package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.InvoiceEntity;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 1.11.12
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface InvoiceDaoInterface extends DaoInterface<InvoiceEntity> {

	public List<InvoiceEntity> findAllProformaForPartner(PartnerEntity partnerEntity);

	public List<InvoiceEntity> findAllForPartner(PartnerEntity partnerEntity);

	public List<InvoiceEntity> findNotPaid(int limit, int offset);

	/**
	 * Hleda faktury, ktere jsou nezaplacene a v dusledku jejich nezaplaceni neni zobrazena nejaka sada reklam
	 * @return seznam faktur kvuli jejich nezaplaceni se nezobrazuji reklamy ze sady
	 */
	List<InvoiceEntity> findNotPaidForBundleNotDisplay(Integer partnerID);

	/**
	 * Vraci seznam faktur, ktere nejsou zaplacene a kvuli jejich nezaplaceni se prestanou zobrazovat reklamy v prirazenem AdvertBundle za mene nez 16 dni
	 * @param partnerID partner pro ktereho hledat faktury
	 * @return seznam faktur
	 */
	List<InvoiceEntity> findEndingNotPaid(Integer partnerID);


	/**
	 * Vraci pocet vystavenych proforma faktur
	 * @return pocet vystavenych proforma faktur
	 */
	Long getProformaCount();

	/**
	 * Vtaci pocet zaplacenych proforma faktur
	 * @return poceet zaplacenych proforma faktur
	 */
	Long getPaidProformaCount();

}
