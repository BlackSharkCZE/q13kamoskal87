package cz.kamoska.partner.models.request;

import cz.kamoska.partner.entities.AdvertEntity;
import org.primefaces.model.UploadedFile;

import javax.enterprise.inject.Model;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 14.10.12
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
@Model
public class AdvertModel {

	private AdvertEntity advertEntity;
	private String advertFileName;

	public AdvertModel() {
		advertEntity = new AdvertEntity();
	}

	public String getAdvertFileName() {
		return advertFileName;
	}

	public void setAdvertFileName(String advertFileName) {
		this.advertFileName = advertFileName;
	}

	public AdvertEntity getAdvertEntity() {
		return advertEntity;
	}

	public void setAdvertEntity(AdvertEntity advertEntity) {
		this.advertEntity = advertEntity;
	}
}
