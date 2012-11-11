package cz.kamoska.partner.pojo.kamoska;

import cz.kamoska.partner.entities.AdvertEntity;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 11.11.12
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class AdvertViewWrapper {

	public AdvertEntity advertEntity;
	public Integer viewCount;

	public AdvertViewWrapper() {
	}

	public AdvertViewWrapper(AdvertEntity advertEntity, Integer viewCount) {
		this.advertEntity = advertEntity;
		this.viewCount = viewCount;
	}
}
