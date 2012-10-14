package cz.kamoska.partner.controllers;

import cz.kamoska.partner.models.sessions.AdvertBundleModel;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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

	public String createNewAdvert() {
		return "create-new-advert";
	}

}
