package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.beans.FakturoidDao;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.dao.interfaces.InvoiceDaoInterface;
import cz.kamoska.partner.dao.interfaces.MessageDaoInterface;
import cz.kamoska.partner.entities.AdvertBundleEntity;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.entities.InvoiceEntity;
import cz.kamoska.partner.entities.MessageEntity;
import cz.kamoska.partner.enums.AdvertState;
import cz.kamoska.partner.enums.InvoiceType;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.pojo.fakturoid.Invoice;
import cz.kamoska.partner.pojo.fakturoid.InvoiceStatus;
import cz.kamoska.partner.pojo.kamoska.DefaultMessage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

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
	@Inject
	private DefaultMessages defaultMessages;

	@EJB
	private InvoiceDaoInterface invoiceDaoInterface;
	@EJB
	private FakturoidDao fakturoidDao;
	@EJB
	private AdvertBundleDaoInterface advertBundleDaoInterface;
	@EJB
	private MessageDaoInterface messageDaoInterface;

	@Schedule(hour = "23", minute = "15", second = "10")
	public void process() {
		try {
			checkPaidInvoices();
		} catch (Exception e) {
			logger.severe("Exception in process method checkPaidInvoices: " +  e.getMessage());
			logger.throwing(this.getClass().getSimpleName(), "process", e);
		}

		try {
			createNewProforma();
		} catch (Exception e) {
			logger.severe("Exception in process method createNewProforma: " + e.getMessage());
			logger.throwing(this.getClass().getSimpleName(), "process", e);
		}

	}

	private void createNewProforma() {
		try {

			List<AdvertBundleEntity> allRequiredNewProforma = advertBundleDaoInterface.findAllRequiredNewProforma();
			for (AdvertBundleEntity advertBundle : allRequiredNewProforma) {

				// pro kazdou advertBundle je nutne vytvorit novou proforma-fakturu a vytvorit hlasku pro partnera.

				{
					// neni vystavena faktura, takze fakturu vystavime
					BigDecimal invoicePrice = advertBundle.getAdvertPriceGroupEntity().getPriceCzk();
					Invoice invoice = new Invoice(advertBundle.getPartnerEntity().getFakturoidId(), invoicePrice);
					InvoiceEntity invoiceEntity = fakturoidDao.createInvoice(invoice);
					if (invoiceEntity == null) {
						logger.warning("Invoice for partner " + advertBundle.getPartnerEntity() + " for advertBundle " + advertBundle + " was not created!");
						//todo odeslat email klukum, ze nebyla vystavena faktura
					} else {
						invoiceEntity.setAdvertBundleEntity(advertBundle);
						invoiceEntity.setInvoiceType(InvoiceType.PROFORMA);
						invoiceEntity.setPrice(invoicePrice);

						DefaultMessage message = defaultMessages.getMessageFor(DefaultMessages.MessageCategory.EXTEND_PROFORMA_CREATED);
						message.replaceAll("{BUNDLE_NAME}", advertBundle.getName());
						message.replaceAll("{INVOICE_NUMBER}", invoiceEntity.getNumber());
						SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
						message.replaceAll("{EXPIRE}", sdf.format(advertBundle.getValidTo()));

						MessageEntity messageEntity = new MessageEntity();
						messageEntity.setBody(message.getBody());
						messageEntity.setTitle(message.getTitle());
						messageEntity.setMessageType(message.getMessageType());
						messageEntity.setGroupUid(String.valueOf(Calendar.getInstance().getTime().getTime()));
						messageEntity.setPartner(advertBundle.getPartnerEntity());
						messageEntity.setPublishDate(Calendar.getInstance().getTime());

						SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
						if (!messageSaveResult.success) {
							logger.warning("Can not save system message " + messageEntity);
						} else {
							logger.info("Message " + messageEntity + " saved");
						}

						SaveDomainResult<InvoiceEntity> saveResult = invoiceDaoInterface.save(invoiceEntity);
						if (!saveResult.success) {
							logger.severe("Can not save InvoiceEntity: " + invoiceEntity );
						} else {
							logger.info("InvoiceEntity saved: " + saveResult.item);
						}
					}
				}

			}

		} catch (Exception e) {
			logger.severe("Can not create new proforma: " + e.getMessage());
			logger.throwing(this.getClass().getSimpleName(), "createNewProforma", e);
		}
	}

	private void checkPaidInvoices() {
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
								update.item.getAdvertBundleEntity().setStatus(AdvertState.ACTIVE);
								SaveDomainResult<AdvertBundleEntity> update1 = advertBundleDaoInterface.update(update.item.getAdvertBundleEntity());
								if (!update1.success) {
									logger.severe("Can not update AdvertBundleEntity: " + update.item.getAdvertBundleEntity());
								} else {


									DefaultMessage message = defaultMessages.getMessageFor(DefaultMessages.MessageCategory.INVOICE_PAID);
									message.replaceAll("{BUNDLE_NAME}", update1.item.getName());
									message.replaceAll("{INVOICE_NUMBER}", invoiceEntity.getNumber());
									message.replaceAll("{PROFORMA_NUMBER}", ie.getNumber());
									SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
									message.replaceAll("{VALID_TO}", sdf.format(validUntil.getTime()));



									MessageEntity messageEntity = new MessageEntity();
									messageEntity.setBody(message.getBody());
									messageEntity.setTitle(message.getTitle());
									messageEntity.setMessageType(message.getMessageType());
									messageEntity.setGroupUid(String.valueOf(Calendar.getInstance().getTime().getTime()));
									messageEntity.setPartner(ie.getAdvertBundleEntity().getPartnerEntity());
									messageEntity.setPublishDate(Calendar.getInstance().getTime());

									SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
									if (!messageSaveResult.success) {
										logger.warning("Can not save system message " + messageEntity);
									} else {
										logger.info("Message " + messageEntity + " saved");
									}
								}

							} else {
								logger.severe("Can not update Invoice " + ie);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.severe("Exception Occurs in checkPaidInvoices");
			logger.throwing(this.getClass().getSimpleName(), "checkPaidInvoices", e);
		}
	}

}
