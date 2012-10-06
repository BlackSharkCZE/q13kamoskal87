package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.MessagePartnerStatusEntity;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface MessagePartnerStatusDaoInterface extends DaoInterface<MessagePartnerStatusEntity> {

	/**
	 * Hleda seznam zprav ({@link MessagePartnerStatusEntity}) urcenych pro daneho partnera
	 * @param partnerEntity partner, pro ktereho se zpravy hledaji
	 * @param offset pozice od ktere se maji inzeraty pratit (pro potreby strankovani)
	 * @param limit pocet zprav, ktery se ma vratit (pro potreby strankovani)
	 * @return kolekce nalezenych zprav (nesmazanych)
	 */
	List<MessagePartnerStatusEntity> findForPartner(PartnerEntity partnerEntity, int offset, int limit);

	/**
	 * Hleda seznam zprav ({@link MessagePartnerStatusEntity}) urcenych pro daneho partnera
	 * @param partnerId ID partnera pro ktereho hledame seznam zprav
	 * @param offset pozice od ktere se maji inzeraty pratit (pro potreby strankovani)
	 * @param limit pocet zprav, ktery se ma vratit (pro potreby strankovani)
	 * @return kolekce nalezenych zprav (nesmazanych)
	 */
	List<MessagePartnerStatusEntity> findForPartner(Integer partnerId, int offset, int limit);

}
