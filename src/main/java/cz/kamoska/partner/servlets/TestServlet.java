package cz.kamoska.partner.servlets;

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
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 18.11.12
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "TestServlet", urlPatterns = "/tests")
public class TestServlet extends HttpServlet {

	@Inject
	private Logger logger;

	@EJB
	private FakturoidDao fakturoidDao;
	@EJB
	private InvoiceDaoInterface invoiceDaoInterface;

	@EJB
	private AdvertBundleDaoInterface advertBundleDaoInterface;


	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


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
						invoiceEntity.setInvoiceType(InvoiceType.FAKTURA);
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


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
}
