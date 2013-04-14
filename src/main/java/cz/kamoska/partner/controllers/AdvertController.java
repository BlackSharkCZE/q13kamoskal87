package cz.kamoska.partner.controllers;

import cz.kamoska.partner.beans.FakturoidDao;
import cz.kamoska.partner.beans.singletons.DefaultMessages;
import cz.kamoska.partner.beans.singletons.MailerBean;
import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.*;
import cz.kamoska.partner.entities.*;
import cz.kamoska.partner.enums.AdvertState;
import cz.kamoska.partner.enums.InvoiceType;
import cz.kamoska.partner.enums.PartnerGroups;
import cz.kamoska.partner.models.request.AdvertModel;
import cz.kamoska.partner.models.sessions.AdvertBundleModel;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.pojo.fakturoid.Invoice;
import cz.kamoska.partner.pojo.kamoska.DefaultMessage;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import cz.kamoska.partner.support.FileTemplateLoader;
import cz.kamoska.partner.support.PictureUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 14.10.12
 * Time: 17:51
 * Kontroler pro navigaci pri zakladani nove reklamy
 */
@Named
@SessionScoped
public class AdvertController implements Serializable {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Inject
	private AdvertModel advertModel;
	@Inject
	private LoggedInPartner loggedInPartner;
	@Inject
	private FacesMessageProvider fmp;
	@Inject
	private AdvertBundleModel advertBundleModel;
	@Inject
	private FacesMessageProvider facesMessageProvider;
	@Inject
	private FileTemplateLoader fileTemplateLoader;


	@EJB
	private PictureDaoInterface pictureDaoInterface;
	@EJB
	private SectionDaoInterface sectionDaoInterface;
	@EJB
	private AdvertDaoInterface advertDaoInterface;
	@EJB
	private FakturoidDao fakturoidDao;
	@EJB
	private MailerBean mailerBean;
	@EJB
	private InvoiceDaoInterface invoiceDaoInterface;
	@Inject
	private DefaultMessages defaultMessages;
	@EJB
	private MessageDaoInterface messageDaoInterface;


	private PictureEntity pictureEntity;

	public AdvertController() {
		pictureEntity = new PictureEntity();
	}

	public String createNewAdvert() {
		if (advertModel.getAdvertEntity() != null) {
			advertModel.setAdvertEntity(new AdvertEntity());
		}
		return "create-new-advert";
	}

	public void changeState(AdvertEntity entity) {
		AdvertEntity e = entity;
		if (e == null) {
			logger.error("Advert entity is null");
		} else {
			advertDaoInterface.update(entity);
		}
	}

	@RolesAllowed("admin")
	public String acceptTip(AdvertEntity advertEntity) {
		if (advertEntity != null) {
			advertEntity.setAcceptDate(Calendar.getInstance().getTime());
			advertEntity.setState(AdvertState.ACTIVE);
			SaveDomainResult<AdvertEntity> updateResult = advertDaoInterface.update(advertEntity);
			if (!updateResult.success) {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.accept.failed"), FacesContext.getCurrentInstance());
			} else {
				DefaultMessage message = defaultMessages.getMessageFor(DefaultMessages.MessageCategory.ADVERT_ACCEPT);
				message.replaceAll("{ADVERT_TITLE}", advertEntity.getTitle());

				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setBody(message.getBody());
				messageEntity.setTitle(message.getTitle());
				messageEntity.setMessageType(message.getMessageType());
				messageEntity.setGroupUid(String.valueOf(Calendar.getInstance().getTime().getTime()));
				messageEntity.setPartner(advertEntity.getBundleEntity().getPartnerEntity());
				messageEntity.setPublishDate(Calendar.getInstance().getTime());

				SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
				if (!messageSaveResult.success) {
					logger.warn("Can not save system message " + messageEntity);
				} else {
					logger.info("Message " + messageEntity + " saved");
				}
			}
		} else {
			logger.warn("Can not accept null advertEntity");
		}
		return null;
	}

	@RolesAllowed("admin")
	public String rejectSelectedAdvert() {
		if (advertModel.getAdvertEntity().getId() == null || advertModel.getRejectMessage() == null) {
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.reject.advert-or-message-null"), FacesContext.getCurrentInstance());
		} else {
			advertModel.setAdvertEntity(advertDaoInterface.findByPrimaryKey(AdvertEntity.class, advertModel.getAdvertEntity().getId()));
			advertModel.getAdvertEntity().setState(AdvertState.REJECTED);
			advertModel.getAdvertEntity().setRejectDate(Calendar.getInstance().getTime());
			advertModel.getAdvertEntity().setRejectMessage(advertModel.getRejectMessage());
			SaveDomainResult<AdvertEntity> update = advertDaoInterface.update(advertModel.getAdvertEntity());
			if (!update.success) {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.reject.failed"), FacesContext.getCurrentInstance());
			} else {
				DefaultMessage message = defaultMessages.getMessageFor(DefaultMessages.MessageCategory.ADVERT_REJECT);
				message.replaceAll("{ADVERT_TITLE}", advertModel.getAdvertEntity().getTitle());

				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setBody(message.getBody());
				messageEntity.setTitle(message.getTitle());
				messageEntity.setMessageType(message.getMessageType());
				messageEntity.setGroupUid(String.valueOf(Calendar.getInstance().getTime().getTime()));
				messageEntity.setPartner(advertModel.getAdvertEntity().getBundleEntity().getPartnerEntity());
				messageEntity.setPublishDate(Calendar.getInstance().getTime());

				SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
				if (!messageSaveResult.success) {
					logger.warn("Can not save system message " + messageEntity);
				} else {
					logger.info("Message " + messageEntity + " saved");
				}
			}
		}
		return null;
	}


	public String dropAdvert() {
		advertModel.setAdvertEntity(advertDaoInterface.findByPrimaryKey(AdvertEntity.class, advertModel.getAdvertEntity().getId()));
		if (advertModel.getAdvertEntity().getBundleEntity().getPartnerEntity().equals(loggedInPartner.getPartner()) || loggedInPartner.getPartner().getRoles().contains(PartnerGroups.GROUP_ADMIN.toString())) {
			final String systemName = advertModel.getAdvertEntity().getPictureEntity().getSystemName().split("\\.")[0];
			final String[] parts = advertModel.getAdvertEntity().getPictureEntity().getOrigName().split("\\.");
			final String suffix = parts[parts.length - 1].toLowerCase();

			Path thumbs = FileSystems.getDefault().getPath(MainConfig.IMAGE_STORE_ROOT_PATH + "/" + loggedInPartner.getPartner().getId(), advertModel.getAdvertEntity().getPictureEntity().getSystemName());
			Path origFile = FileSystems.getDefault().getPath(MainConfig.IMAGE_STORE_ROOT_PATH + "/" + loggedInPartner.getPartner().getId() + "/" + MainConfig.SRC_FILE_FOLDER, systemName + "." + suffix);
			try {
				Files.deleteIfExists(thumbs);
			} catch (IOException e) {
				logger.error("Can not delete ThumbBile " + thumbs, e);
			}

			try {
				Files.deleteIfExists(origFile);
			} catch (IOException e) {
				logger.error("Can not delete OrigFile: " + origFile, e);

			}

			advertModel.getAdvertEntity().setState(AdvertState.DELETED);
			SaveDomainResult<AdvertEntity> update = advertDaoInterface.update(advertModel.getAdvertEntity());

			if (!update.success) {
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.drop.failed"), FacesContext.getCurrentInstance());
				logger.error("Can not drop advert: " + advertModel.getAdvertEntity());
			}
		} else {
			logger.warn("Can not drop advert of other user. Current advert is owned by " + advertModel.getAdvertEntity().getBundleEntity().getPartnerEntity() + " but current partner is " + loggedInPartner.getPartner());
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.drop.failed-partner-is-not-owner"), FacesContext.getCurrentInstance());
		}


		return null;
	}

	public String editAdvert() {
		pictureEntity = advertModel.getAdvertEntity().getPictureEntity();
		AdvertEntity adv = advertModel.getAdvertEntity();
		return "edit-advert";
	}

	public String updateAdvert() {

		String res = null;

		if (advertModel.getAdvertEntity() != null) {
			// najit obrazek v DB, ktery ma byt prirazeny k reklamne
			PictureEntity picture = pictureDaoInterface.findByPrimaryKey(PictureEntity.class, pictureEntity.getId());
			if (picture != null) {
				advertModel.getAdvertEntity().setPictureEntity(picture);
				advertModel.getAdvertEntity().setState(AdvertState.WAITING_TO_ACK);
				List<SectionEntity> requiredSectionEntity = sectionDaoInterface.findAllAlwaysSelected();
				for (SectionEntity se : requiredSectionEntity) {
					if (!advertModel.getAdvertEntity().getSectionEntityList().contains(se)) {
						advertModel.getAdvertEntity().getSectionEntityList().add(se);
					}
				}
				advertModel.getAdvertEntity().setBundleEntity(advertBundleModel.getCurrentItem());

				AdvertEntity ae = advertDaoInterface.findByPrimaryKey(AdvertEntity.class, advertModel.getAdvertEntity().getId());

				ae.setTitle(advertModel.getAdvertEntity().getTitle());
				ae.setBody(advertModel.getAdvertEntity().getBody());
				ae.setUrl(advertModel.getAdvertEntity().getUrl());
				ae.setPictureEntity(advertModel.getAdvertEntity().getPictureEntity());

				ae.setSectionEntityList(advertModel.getAdvertEntity().getSectionEntityList());

				ae.setRejectDate(null);
				ae.setRejectMessage(null);
				ae.setState(AdvertState.WAITING_TO_ACK);

				SaveDomainResult<AdvertEntity> save = advertDaoInterface.update(ae);
				advertModel.setAdvertEntity(ae);

				if (save.success) {
					logger.info("Advert Entity updated. " + save.item);
					advertModel.setAdvertEntity(new AdvertEntity());
					setPictureEntity(new PictureEntity());
					res = "update-advert-successful";
				} else {
					FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.save.error.db-error"), FacesContext.getCurrentInstance());
					logger.error("Update advert " + advertModel.getAdvertEntity() + " failed.");
				}

			} else {
				logger.warn("Can not find picture entity by ID: " + pictureEntity.getId());
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.save.error.missing-picture"), FacesContext.getCurrentInstance());
			}
		} else {
			logger.warn("Can not update, becouse advertModel.getAdvertEntity() is null");
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.save.error.advert-model-null"), FacesContext.getCurrentInstance());

		}

		return res;
	}


	public String saveAdvert() {

		String res = null;

		if (advertModel.getAdvertEntity() != null) {
			// najit obrazek v DB, ktery ma byt prirazeny k reklamne
			PictureEntity picture = pictureDaoInterface.findByPrimaryKey(PictureEntity.class, pictureEntity.getId());
			if (picture != null) {
				advertModel.getAdvertEntity().setPictureEntity(picture);
				advertModel.getAdvertEntity().setState(AdvertState.WAITING_TO_ACK);
				List<SectionEntity> requiredSectionEntity = sectionDaoInterface.findAllAlwaysSelected();
				for (SectionEntity se : requiredSectionEntity) {
					if (!advertModel.getAdvertEntity().getSectionEntityList().contains(se)) {
						advertModel.getAdvertEntity().getSectionEntityList().add(se);
					}
				}
				AdvertBundleEntity advertBundle = advertBundleModel.getCurrentItem();
				advertModel.getAdvertEntity().setBundleEntity(advertBundle);
				SaveDomainResult<AdvertEntity> save = advertDaoInterface.save(advertModel.getAdvertEntity());

				if (save.success) {

					logger.info("Advert Entity saved. " + save.item);

					if (advertBundle.getInvoiceEntities() == null || advertBundle.getInvoiceEntities().isEmpty()) {
						// neni vystavena faktura, takze fakturu vystavime
						BigDecimal invoicePrice = advertModel.getAdvertEntity().getBundleEntity().getAdvertPriceGroupEntity().getPriceCzk();
						Invoice invoice = new Invoice(loggedInPartner.getPartner().getFakturoidId(), invoicePrice);
						InvoiceEntity invoiceEntity = fakturoidDao.createInvoice(invoice);
						if (invoiceEntity == null) {
							logger.warn("Invoice for partner " + loggedInPartner.getPartner() + " for advertBundle " + advertModel.getAdvertEntity() + " was not created!");
							//todo odeslat email klukum, ze nebyla vystavena faktura
						} else {
							invoiceEntity.setAdvertBundleEntity(save.item.getBundleEntity());
							invoiceEntity.setInvoiceType(InvoiceType.PROFORMA);
							invoiceEntity.setPrice(invoicePrice);
							//TODO zde se musi spravne urcovat DPH a spravne prepocitavat castka. Musi dodat Radek
							invoiceEntity.setPriceIncVat(invoicePrice);
//							invoiceEntity.setPriceIncVat(invoicePrice.multiply(BigDecimal.valueOf(1.2)));


							DefaultMessage message = defaultMessages.getMessageFor(DefaultMessages.MessageCategory.TIPS_PROFORMA_CREATED);
							message.replaceAll("{BUNDLE_NAME}", save.item.getBundleEntity().getName());
							message.replaceAll("{INVOICE_NUMBER}", invoiceEntity.getNumber());

							MessageEntity messageEntity = new MessageEntity();
							messageEntity.setBody(message.getBody());
							messageEntity.setTitle(message.getTitle());
							messageEntity.setMessageType(message.getMessageType());
							messageEntity.setGroupUid(String.valueOf(Calendar.getInstance().getTime().getTime()));
							messageEntity.setPartner(loggedInPartner.getPartner());
							messageEntity.setPublishDate(Calendar.getInstance().getTime());

							String emailTemplate = fileTemplateLoader.loadFileFromResources("invoice-create-proforma-email-template.html")
								 .replace("{PROFORMA}", invoiceEntity.getNumber())
								 .replace("{ADVERT_BUNDLE}", invoiceEntity.getAdvertBundleEntity().getName())
								 .replace("{PROFORMA_URL}", invoiceEntity.getFakturoidUrl());

							mailerBean.sendEmail(emailTemplate, MainConfig.INVOICE_CREATE_PROFORMA_SUBJECT, Arrays.asList(invoiceEntity.getAdvertBundleEntity().getPartnerEntity().getEmail()), MainConfig.EMAIL_FROM, null, true);

							SaveDomainResult<MessageEntity> messageSaveResult = messageDaoInterface.save(messageEntity);
							if (!messageSaveResult.success) {
								logger.warn("Can not save system message " + messageEntity);
							} else {
								logger.info("Message " + messageEntity + " saved");
							}


							SaveDomainResult<InvoiceEntity> saveResult = invoiceDaoInterface.save(invoiceEntity);
							if (!saveResult.success) {
								logger.error("Can not save InvoiceEntity: " + invoiceEntity);
							} else {
								logger.info("InvoiceEntity saved: " + saveResult.item);
							}
						}
					}
					advertModel.setAdvertEntity(new AdvertEntity());
					setPictureEntity(new PictureEntity());
					res = "save-advert-successful";
				} else {
					FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.save.error.db-error"), FacesContext.getCurrentInstance());
					logger.error("Save advert " + advertModel.getAdvertEntity() + " failed.");
				}

			} else {
				logger.warn("Can not find picture entity by ID: " + pictureEntity.getId());
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.save.error.missing-picture"), FacesContext.getCurrentInstance());
			}
		} else {
			logger.warn("Can not save new Advert, becouse advertModel.getAdvertEntity() is null");
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, facesMessageProvider.getLocalizedMessage("advert.save.error.advert-model-null"), FacesContext.getCurrentInstance());

		}

		return res;
	}


	public String cancelAdvert() {
		return "cancel-advert";
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uFile = event.getFile();
		FacesMessageCreate.addMessage(FacesMessage.SEVERITY_INFO, "File " + uFile.getFileName() + " is uploaded!", FacesContext.getCurrentInstance());

		pictureEntity = new PictureEntity();

		pictureEntity.setPartnerEntity(loggedInPartner.getPartner());
		pictureEntity.setSize(uFile.getSize());
		pictureEntity.setContentType(uFile.getContentType());
		pictureEntity.setOrigName(uFile.getFileName());

		BufferedImage bi = null;
		try {
			bi = PictureUtils.createFormInputStream(uFile.getInputstream());

		} catch (IOException e) {
			logger.error("Can not read image ", e);
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, fmp.getLocalizedMessage("image.upload.failed.can-not-read-image-data"), FacesContext.getCurrentInstance());
		}


		if (bi != null) {
			pictureEntity.setOrigHeight(bi.getHeight());
			pictureEntity.setOrigWidth(bi.getWidth());
			final String newName = String.valueOf(Calendar.getInstance().getTime().getTime());
			pictureEntity.setSystemName(newName + ".jpg");

			BufferedImage croppedImage = PictureUtils.cropImageByCenter(bi);
			if (croppedImage != null) {
				logger.info("Crop image success");
				BufferedImage resizedImage = PictureUtils.resizeImage(croppedImage, 60, 60);
				if (resizedImage != null) {
					logger.info("Resize image successful");
					final String thumbName = MainConfig.IMAGE_STORE_ROOT_PATH + "/" + loggedInPartner.getPartner().getId() + "/" + newName + ".jpg";
					if (PictureUtils.saveAsJPG(thumbName, resizedImage)) {
						final String[] parts = uFile.getFileName().split("\\.");
						final String suffix = parts[parts.length - 1].toLowerCase();
						Path path = FileSystems.getDefault().getPath(MainConfig.IMAGE_STORE_ROOT_PATH + "/" + loggedInPartner.getPartner().getId() + "/" + MainConfig.SRC_FILE_FOLDER, newName + "." + suffix);
						try (
							 InputStream in = uFile.getInputstream();
							 OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW);
						) {
							int read = 0;
							byte[] bytes = new byte[1024];
							while ((read = in.read(bytes)) != -1) {
								out.write(bytes, 0, read);
							}

							SaveDomainResult<PictureEntity> saveResult = pictureDaoInterface.save(pictureEntity);
							if (!saveResult.success) {
								logger.warn("Can not save " + pictureEntity + " to DB!");
							}

						} catch (Exception e) {
							logger.error("Can not save SRC file for file " + pictureEntity, e);
						}

						logger.info("New file saved!");
					} else {
						logger.error("Can not save uploaded image");
					}
				} else {
					logger.error("Can not resize image");
				}
			} else {
				logger.error("Can not crop IMAGE");
			}

		} else {
			logger.error("Can not create buffered Image");
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR, fmp.getLocalizedMessage("image.upload.failed.can-not-create-image"), FacesContext.getCurrentInstance());
		}


	}

	public PictureEntity getPictureEntity() {
		return pictureEntity;
	}

	public void setPictureEntity(PictureEntity pictureEntity) {
		this.pictureEntity = pictureEntity;
	}
}
