package cz.kamoska.partner.models.request;

import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.entities.AdvertEntity;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 14.10.12
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
@Model
public class AdvertModel {

	@EJB
	private SectionDaoInterface sectionDaoInterface;

	private AdvertEntity advertEntity;
	private String rejectMessage;



	public AdvertModel() {
		advertEntity = new AdvertEntity();
	}

	@PostConstruct
	public void postConstruct() {
		advertEntity.setSectionEntityList(sectionDaoInterface.findAllAlwaysSelected());
	}

	public AdvertEntity getAdvertEntity() {
		return advertEntity;
	}

	public void setAdvertEntity(AdvertEntity advertEntity) {
		this.advertEntity = advertEntity;
	}

	public String getRejectMessage() {
		return rejectMessage;
	}

	public void setRejectMessage(String rejectMessage) {
		this.rejectMessage = rejectMessage;
	}
}
