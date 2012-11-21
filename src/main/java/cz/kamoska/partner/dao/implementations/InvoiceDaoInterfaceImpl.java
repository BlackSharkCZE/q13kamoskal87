package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.InvoiceDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.InvoiceEntity;
import cz.kamoska.partner.entities.PartnerEntity;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 1.11.12
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class InvoiceDaoInterfaceImpl extends DaoTemplate<InvoiceEntity> implements InvoiceDaoInterface {

	@Override
	public List<InvoiceEntity> findAllProformaForPartner(PartnerEntity partnerEntity) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("partnerID", partnerEntity.getId());
		return findListByNamedQuery("InvoiceEntity.findAllProformaForPartner", 0, -1, params);
	}

	@Override
	public List<InvoiceEntity> findAllForPartner(PartnerEntity partnerEntity) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("partnerID", partnerEntity.getId());
		return findListByNamedQuery("InvoiceEntity.findAllForPartner", 0, -1, params);
	}

	@Override
	public List<InvoiceEntity> findNotPaid(int limit, int offset) {
		return findListByNamedQuery("InvoiceEntity.findNotPaid", offset, limit, null);

	}

	@Override
	public List<InvoiceEntity> findNotPaidForBundleNotDisplay(Integer partnerID) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("partnerID", partnerID);
		return findListByNamedQuery("InvoiceEntity.findNotPaidForBundleNotDisplay", 0, -1, params);
	}

	@Override
	public List<InvoiceEntity> findEndingNotPaid(Integer partnerID) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("partnerID", partnerID);
		return findListByNamedQuery("InvoiceEntity.native.findEndigNotPaid", 0, -1, params);
	}

}
