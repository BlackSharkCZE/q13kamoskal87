package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.beans.FakturoidDao;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.interfaces.InvoiceDaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.entities.InvoiceEntity;
import cz.kamoska.partner.enums.InvoiceType;
import cz.kamoska.partner.pojo.fakturoid.Invoice;
import cz.kamoska.partner.pojo.fakturoid.InvoiceStatus;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 18.11.12
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class InvoiceChecker {

	@Inject
	private Logger logger;
	@EJB
	private InvoiceDaoInterface invoiceDaoInterface;
	@EJB
	private FakturoidDao fakturoidDao;
	@EJB
	private AdvertBundleDaoInterface advertBundleDaoInterface;

	@Schedule(hour = "23", minute = "15", second = "10")
	public void process() {
		try {
			internalProcess();
		} catch (Exception e) {
			logger.error("Exception in process method on InvoiceChecker", e);
		}
	}

	private void internalProcess() {
		try {
			List<InvoiceEntity> notPaid = invoiceDaoInterface.findNotPaid(10, 0);
			for (InvoiceEntity ie : notPaid) {
				Invoice invoice = fakturoidDao.findById(ie.getFakturoidId());
				if (invoice != null) {
					if (invoice.getInvoiceStatus() == InvoiceStatus.PAID && invoice.getRelatedId() != null) {
						ie.setPaid(invoice.getPaidAt());
						Invoice paidInvoice = fakturoidDao.findById(invoice.getRelatedId());
						if (paidInvoice != null) {
							InvoiceEntity invoiceEntity = new InvoiceEntity();
							invoiceEntity.setFakturoidId(paidInvoice.getId());
							invoiceEntity.setFakturoidUrl(paidInvoice.getPublicHtmlUrl());
							invoiceEntity.setInvoiceType(paidInvoice.isProforma() ? InvoiceType.PROFORMA : InvoiceType.FAKTURA);
							invoiceEntity.setNumber(paidInvoice.getNumber());
							invoiceEntity.setPaid(paidInvoice.getPaidAt());
							invoiceEntity.setPrice(ie.getPrice());
							invoiceEntity.setPriceIncVat(ie.getPriceIncVat());
							invoiceEntity.setAdvertBundleEntity(ie.getAdvertBundleEntity());
							ie.setInvoice(invoiceEntity);
							SaveDomainResult<InvoiceEntity> update = invoiceDaoInterface.update(ie);
							if (update.success) {
								Calendar validUntil = new GregorianCalendar();
								validUntil.setTime(paidInvoice.getPaidAt());
								AdvertPriceGroupEntity apge = update.item.getAdvertBundleEntity().getAdvertPriceGroupEntity();
								validUntil.add(Calendar.DAY_OF_MONTH, apge.getDuration());
								update.item.getAdvertBundleEntity().setValidTo(validUntil.getTime());
								update.item.getAdvertBundleEntity().setValidFrom(invoiceEntity.getPaid());
								SaveDomainResult<AdvertBundleEntity> update1 = advertBundleDaoInterface.update(update.item.getAdvertBundleEntity());
								if (!update1.success) {
									logger.error("Can not update AdvertBundleEntity: " + update.item.getAdvertBundleEntity());
								}
							} else {
								logger.error("Can not update Invoice " + ie);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception Occurs in internalProcess", e);
		}
	}

}
