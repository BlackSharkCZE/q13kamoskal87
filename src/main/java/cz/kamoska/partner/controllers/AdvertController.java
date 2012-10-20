package cz.kamoska.partner.controllers;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.dao.interfaces.PictureDaoInterface;
import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.entities.PictureEntity;
import cz.kamoska.partner.entities.SectionEntity;
import cz.kamoska.partner.enums.AdvertState;
import cz.kamoska.partner.models.request.AdvertModel;
import cz.kamoska.partner.models.sessions.AdvertBundleModel;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.support.FacesMessageCreate;
import cz.kamoska.partner.support.FacesMessageProvider;
import cz.kamoska.partner.support.PictureUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
@RequestScoped
public class AdvertController implements Serializable {

	private final Logger logger = Logger.getLogger(AdvertController.class);

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
	@EJB
	private PictureDaoInterface pictureDaoInterface;
	@EJB
	private SectionDaoInterface sectionDaoInterface;
	@EJB
	private AdvertDaoInterface advertDaoInterface;



	private PictureEntity pictureEntity;

	public AdvertController() {
		pictureEntity = new PictureEntity();
	}

	public String createNewAdvert() {

		return "create-new-advert";
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
				advertModel.getAdvertEntity().setBundleEntity(advertBundleModel.getCurrentItem());
				SaveDomainResult<AdvertEntity> save = advertDaoInterface.save(advertModel.getAdvertEntity());
				if (save.success) {
					logger.info("Advert Entity saved. " + save.item);
					res = "save-advert-successful";
				} else {
					FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR,facesMessageProvider.getLocalizedMessage("advert.save.error.db-error"), FacesContext.getCurrentInstance());
					logger.error("Save advert " + advertModel.getAdvertEntity() + " failed.");
				}

			} else {
				logger.warn("Can not find picture entity by ID: " + pictureEntity.getId());
				FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR,facesMessageProvider.getLocalizedMessage("advert.save.error.missing-picture"), FacesContext.getCurrentInstance());
			}
		} else {
			logger.warn("Can not save new Advert, becouse advertModel.getAdvertEntity() is null");
			FacesMessageCreate.addMessage(FacesMessage.SEVERITY_ERROR,facesMessageProvider.getLocalizedMessage("advert.save.error.advert-model-null"), FacesContext.getCurrentInstance());

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
			final String newName = Calendar.getInstance().getTime().getTime() + ".jpg";
			pictureEntity.setSystemName(newName);

			BufferedImage croppedImage = PictureUtils.cropImageByCenter(bi);
			if (croppedImage != null) {
				logger.info("Crop image success");
				BufferedImage resizedImage = PictureUtils.resizeImage(croppedImage, 60, 60);
				if (resizedImage != null) {
					logger.info("Resize image successful");
					final String thumbName = MainConfig.IMAGE_STORE_ROOT_PATH + "/" + loggedInPartner.getPartner().getId() + "/" + newName;
					if (PictureUtils.saveAsJPG(thumbName, resizedImage)) {
						Path path = FileSystems.getDefault().getPath(MainConfig.IMAGE_STORE_ROOT_PATH + "/" + loggedInPartner.getPartner().getId() + "/" + MainConfig.SRC_FILE_FOLDER, pictureEntity.getOrigName());
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
