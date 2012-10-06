package cz.kamoska.partner.support;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.10.12
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class FacesMessageProvider implements Serializable {

	private static final String RESOURCE_BUNDLE_NAME = "i18n/messages";

	public String getLocalizedMessage(String key) {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		return getLocalizedMessage(key, locale);
	}

	public String getLocalizedMessage(String key, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
		if (bundle != null) {
			return bundle.getString(key);
		} else {
			return "";
		}
	}

}
