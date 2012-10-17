package cz.kamoska.partner.controllers;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.PictureDaoInterface;
import cz.kamoska.partner.entities.PictureEntity;
import cz.kamoska.partner.models.request.AdvertModel;
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
import java.io.*;
import java.nio.file.*;
import java.util.Calendar;

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

	private PictureEntity pictureEntity;

	@EJB
	private PictureDaoInterface pictureDaoInterface;


	public String createNewAdvert() {
		return "create-new-advert";
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
								logger.warn("Can not save " + pictureEntity  + " to DB!");
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
