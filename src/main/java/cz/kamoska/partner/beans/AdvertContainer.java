package cz.kamoska.partner.beans;

import cz.kamoska.partner.entities.AdvertEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 17.9.13
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public class AdvertContainer implements Comparable<AdvertContainer> {

	public AdvertEntity advertEntity;
	public long displayCount;
	public List<String> sections;

	public AdvertContainer() {
	}

	@Override
	public int compareTo(AdvertContainer o) {
		if (o != null) {
			return (int) (this.displayCount - o.displayCount);
		} else {
			return 1;
		}
	}
}
