package cz.kamoska.partner.models.request;

import cz.kamoska.partner.enums.AdvertState;
import cz.kamoska.partner.support.FacesMessageProvider;

import javax.enterprise.inject.Model;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 21.10.12
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
@Model
public class EnumModel {

	@Inject
	private FacesMessageProvider facesMessageProvider;

	public SelectItem[] getAllPartnerState() {
		SelectItem[] s = new SelectItem[2];
		s[0] = new SelectItem(AdvertState.ACTIVE, facesMessageProvider.getLocalizedMessage(AdvertState.ACTIVE.toString()));
		s[1] = new SelectItem(AdvertState.SUSPEND, facesMessageProvider.getLocalizedMessage(AdvertState.SUSPEND.toString()));
		return s;
	}

}
