package cz.kamoska.partner.models.dao;

import cz.kamoska.partner.dao.interfaces.InvoiceDaoInterface;
import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.InvoiceEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.models.template.AbstractModel;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.11.12
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
@SessionScoped
@Named
public class InvoiceDaoModel  implements Serializable {

	@Inject
	private LoggedInPartner loggedInPartner;

	@EJB
	private InvoiceDaoInterface invoiceDaoInterface;

	// cache pro pristup k datum
	private List<InvoiceEntity> cache;
	private PartnerEntity cacheForPartner;

	@Produces
	@RequestScoped
	@Named
	public List<InvoiceEntity> findNotPaidToNotDisplayAdvertBundle() {
		List<InvoiceEntity> res = invoiceDaoInterface.findNotPaidForBundleNotDisplay(loggedInPartner.getPartner().getId());
		return res;
	}

	@Produces
	@RequestScoped
	@Named
	public List<InvoiceEntity> findEndingNotPaid() {
		List<InvoiceEntity> res = invoiceDaoInterface.findEndingNotPaid(loggedInPartner.getPartner().getId());
		return res;
	}

	public Long getCountOfProforma() {
		return invoiceDaoInterface.getProformaCount();
	}

	public Long getCountOfPaidProforma() {
		return invoiceDaoInterface.getPaidProformaCount();
	}


	public void loadCacheFor(PartnerEntity partner) {
		cache = invoiceDaoInterface.findAllProformaForPartner(partner);
		cacheForPartner = partner;
	}

	public List<InvoiceEntity> getAllInvoiceForPartner(PartnerEntity partner) {
		if (!partner.equals(cacheForPartner)) {
			loadCacheFor(partner);
		}
		return cache;
	}

}
