package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.beans.FakturoidDao;
import cz.kamoska.partner.config.MainConfig;
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
import cz.kamoska.partner.pojo.fakturoid.Invoice;
import cz.kamoska.partner.pojo.fakturoid.InvoiceStatus;
import cz.kamoska.partner.pojo.kamoska.DefaultMessage;
import cz.kamoska.partner.support.FileTemplateLoader;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

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
	@Inject
	private FileTemplateLoader fileTemplateLoader;

	@EJB
	private InvoiceDaoInterface invoiceDaoInterface;
	@EJB
	private FakturoidDao fakturoidDao;
	@EJB
	private AdvertBundleDaoInterface advertBundleDaoInterface;
	@EJB
	private MessageDaoInterface messageDaoInterface;
	@EJB
	private MailerBean mailerBean;

	private Date lastCheckPaidInvoices;
	private Date lastCreateNewProforma;

	@Schedule(hour = "23", minute = "15", second = "10")
	public void process() {
		try {

			checkPaidInvoices();
		} catch (Exception e) {
			logger.severe("Exception in process method checkPaidInvoices: " + e.getMessage());
			logger.throwing(this.getClass().getSimpleName(), "process", e);
		}

		try {
			createNewProforma();
		} catch (Exception e) {
			logger.severe("Exception in process method createNewProforma: " + e.getMessage());
			logger.throwing(this.getClass().getSimpleName(), "process", e);
		}

	}

	public void createNewProforma() {
		logger.info("Create new proforma process start.");
		lastCreateNewProforma = Calendar.getInstance().getTime();
		try {

			List<AdvertBundleEntity> allRequiredNewProforma = advertBundleDaoInterface.findAllRequiredNewProforma();

			if (allRequiredNewProforma != null && !allRequiredNewProforma.isEmpty()) {
				logger.info("There is " + allRequiredNewProforma.size() + " AdvertBundle ready to create new proforma.");
				for (AdvertBundleEntity advertBundle : allRequiredNewProforma) {
					logger.info("Create new proforma for AdvertBundle: " + advertBundle);
					// pro kazdou advertBundle je nutne vytvorit novou proforma-fakturu a vytvorit hlasku pro partnera.


					// neni vystavena faktura, takze fakturu vystavime
					BigDecimal invoicePrice = advertBundle.getAdvertPriceGroupEntity().getPriceCzk();
					Invoice invoice = new Invoice(advertBundle.getPartnerEntity().getFakturoidId(), invoicePrice);
					InvoiceEntity invoiceEntity = fakturoidDao.createInvoice(invoice);
					if (invoiceEntity == null) {
						logger.warning("Invoice for partner " + advertBundle.getPartnerEntity() + " for advertBundle " + advertBundle + " was not created!");
						//todo odeslat email klukum, ze nebyla vystavena faktura
					} else {
						logger.info("Invoice for advertBundle " + advertBundle + " was created!");
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

						String emailTemplate = fileTemplateLoader.loadFileFromResources("invoice-create-proforma-extension-email-template.html")
							 .replace("{PROFORMA}", invoiceEntity.getNumber())
							 .replace("{ADVERT_BUNDLE}", invoiceEntity.getAdvertBundleEntity().getName())
							 .replace("{PROFORMA_URL}", invoiceEntity.getFakturoidUrl());

						logger.info("Proforma "+invoiceEntity.getNumber()+" created and try send notification email to partner: "+invoiceEntity.getAdvertBundleEntity().getPartnerEntity().getEmail());
						mailerBean.sendEmail(emailTemplate, MainConfig.INVOICE_CREATE_PROFORMA_SUBJECT, Arrays.asList(invoiceEntity.getAdvertBundleEntity().getPartnerEntity().getEmail()), MainConfig.EMAIL_FROM, null, true);
						logger.info("Proforma "+ invoiceEntity.getNumber() + "created and e-mail sent to " + invoiceEntity.getAdvertBundleEntity().getPartnerEntity().getEmail());

						SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
						if (!messageSaveResult.success) {
							logger.warning("Can not save system message " + messageEntity);
						} else {
							logger.info("Message " + messageEntity + " saved");
						}

						SaveDomainResult<InvoiceEntity> saveResult = invoiceDaoInterface.save(invoiceEntity);
						if (!saveResult.success) {
							logger.severe("Can not save InvoiceEntity: " + invoiceEntity);
						} else {
							logger.info("InvoiceEntity saved: " + saveResult.item);
						}
					}


				}
			} else {
				logger.info("There is not any advertBundle ready to new proforma.");
			}

		} catch (Exception e) {
			logger.severe("Can not create new proforma: " + e.getMessage());
			logger.throwing(this.getClass().getSimpleName(), "createNewProforma", e);
		} finally {
			logger.info("Create new proforma process finished.");
		}
	}

	public void checkPaidInvoices() {
		logger.info("Check paid invoices process start!");
		lastCheckPaidInvoices = Calendar.getInstance().getTime();

		logger.info("CheckPaidInvoices....");
		try {
			List<InvoiceEntity> notPaid = invoiceDaoInterface.findNotPaid(10, 0);

			if (notPaid != null && !notPaid.isEmpty()) {
				for (InvoiceEntity notPaidProforma : notPaid) {
					Invoice invoice = fakturoidDao.findById(notPaidProforma.getFakturoidId());
					if (invoice != null) {
						if (invoice.getInvoiceStatus() == InvoiceStatus.PAID && invoice.getRelatedId() != null) {
							notPaidProforma.setPaid(invoice.getPaidAt());
							Invoice paidInvoice = fakturoidDao.findById(invoice.getRelatedId());
							if (paidInvoice != null) {
								InvoiceEntity paidInvoceEntity = new InvoiceEntity();
								paidInvoceEntity.setFakturoidId(paidInvoice.getId());
								paidInvoceEntity.setFakturoidUrl(paidInvoice.getPublicHtmlUrl());
								paidInvoceEntity.setInvoiceType(paidInvoice.isProforma() ? InvoiceType.PROFORMA : InvoiceType.FAKTURA);
								paidInvoceEntity.setNumber(paidInvoice.getNumber());
								paidInvoceEntity.setPaid(paidInvoice.getPaidAt());
								paidInvoceEntity.setPrice(notPaidProforma.getPrice());
								paidInvoceEntity.setPriceIncVat(notPaidProforma.getPriceIncVat());
								paidInvoceEntity.setAdvertBundleEntity(notPaidProforma.getAdvertBundleEntity());
								notPaidProforma.setInvoice(paidInvoceEntity);
								SaveDomainResult<InvoiceEntity> update = invoiceDaoInterface.update(notPaidProforma);
								if (update.success) {
									Calendar validUntil = new GregorianCalendar();
									validUntil.setTime(paidInvoice.getPaidAt());
									AdvertPriceGroupEntity apge = update.item.getAdvertBundleEntity().getAdvertPriceGroupEntity();
									validUntil.add(Calendar.DAY_OF_MONTH, apge.getDuration());
									update.item.getAdvertBundleEntity().setValidTo(validUntil.getTime());
									update.item.getAdvertBundleEntity().setValidFrom(paidInvoceEntity.getPaid());
									update.item.getAdvertBundleEntity().setStatus(AdvertState.ACTIVE);
									SaveDomainResult<AdvertBundleEntity> update1 = advertBundleDaoInterface.update(update.item.getAdvertBundleEntity());
									if (!update1.success) {
										logger.severe("Can not update AdvertBundleEntity: " + update.item.getAdvertBundleEntity());
									} else {


										DefaultMessage message = defaultMessages.getMessageFor(DefaultMessages.MessageCategory.INVOICE_PAID);
										message.replaceAll("{BUNDLE_NAME}", update1.item.getName());
										message.replaceAll("{INVOICE_NUMBER}", paidInvoceEntity.getNumber());
										message.replaceAll("{PROFORMA_NUMBER}", notPaidProforma.getNumber());
										SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
										message.replaceAll("{VALID_TO}", sdf.format(validUntil.getTime()));


										String emailTemplate = fileTemplateLoader.loadFileFromResources("invoice-create-email-template.html")
											 .replace("{PROFORMA}", invoice.getNumber())
											 .replace("{FAKTURA}", paidInvoceEntity.getNumber())
											 .replace("{FAKTURA_URL}", paidInvoceEntity.getFakturoidUrl());

										mailerBean.sendEmail(emailTemplate, MainConfig.INVOICE_CREATE_SUBJECT, Arrays.asList(paidInvoceEntity.getAdvertBundleEntity().getPartnerEntity().getEmail()), MainConfig.EMAIL_FROM, null, true);


										MessageEntity messageEntity = new MessageEntity();
										messageEntity.setBody(message.getBody());
										messageEntity.setTitle(message.getTitle());
										messageEntity.setMessageType(message.getMessageType());
										messageEntity.setGroupUid(String.valueOf(Calendar.getInstance().getTime().getTime()));
										messageEntity.setPartner(notPaidProforma.getAdvertBundleEntity().getPartnerEntity());
										messageEntity.setPublishDate(Calendar.getInstance().getTime());

										SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
										if (!messageSaveResult.success) {
											logger.warning("Can not save system message " + messageEntity);
										} else {
											logger.info("Message " + messageEntity + " saved");
										}
									}

								} else {
									logger.severe("Can not update Invoice " + notPaidProforma);
								}
							}
						}
					}
				}
			} else {
				logger.info("There are not not paid invoices in kamoska system.");
			}
		} catch (Exception e) {
			logger.severe("Exception Occurs in checkPaidInvoices");
			logger.throwing(this.getClass().getSimpleName(), "checkPaidInvoices", e);
		} finally {
			logger.info("Check paid invoices process finished!");

		}
	}

	public Date getLastCheckPaidInvoices() {
		return lastCheckPaidInvoices;
	}

	public Date getLastCreateNewProforma() {
		return lastCreateNewProforma;
	}
}
