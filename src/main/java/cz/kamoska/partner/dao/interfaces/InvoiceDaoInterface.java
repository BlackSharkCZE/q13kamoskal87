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

}
