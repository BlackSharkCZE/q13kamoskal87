package cz.kamoska.partner.controllers;

import cz.kamoska.partner.models.request.AdvertModel;
import cz.kamoska.partner.models.sessions.AdvertBundleModel;
import cz.kamoska.partner.support.FacesMessageCreate;
import org.primefaces.event.FileUploadEvent;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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

	@Inject
	private AdvertModel advertModel;

	private String imagePreviewUrl = "http://static.guim.co.uk/sys-images/discussion/avatars/2010/09/02/cta_262190_18/19f9c569-a6ee-42e7-943c-c9a24ff34350/60x60.png";

	public String createNewAdvert() {
		return "create-new-advert";
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessageCreate.addMessage(FacesMessage.SEVERITY_INFO, "File " + event.getFile().getFileName() + " is uploaded!", FacesContext.getCurrentInstance());
		imagePreviewUrl = "http://www.admuncher.com/forum/download/file.php?avatar=1518.jpg";
		advertModel.setAdvertFileName(event.getFile().getFileName());
	}


	public String getImagePreviewUrl() {
		return imagePreviewUrl;
	}

	public void setImagePreviewUrl(String imagePreviewUrl) {
		this.imagePreviewUrl = imagePreviewUrl;
	}
}
